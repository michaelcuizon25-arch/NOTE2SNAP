package com.example.note2snap.activities

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.note2snap.R
import com.example.note2snap.data.AppDatabase
import com.example.note2snap.model.Note
import com.example.note2snap.model.ScanHistory
import com.example.note2snap.utils.WhiteboardRuleEngine
import com.example.note2snap.utils.setOnAnimatedClickListener
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

class ScanFragment : Fragment() {

    private lateinit var viewFinder: PreviewView
    private lateinit var tvInstruction: TextView
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private var isTorchOn = false
    private var loadingDialog: AlertDialog? = null
    private var scanStartTime: Long = 0L

    // Multi-image crop queue variables
    private val rawImageUris = mutableListOf<Uri>()
    private val croppedImageUris = mutableListOf<Uri>()
    private var currentCropIndex = 0

    // 1. Multi-photo gallery picker using the classic app chooser
    private val selectMultipleImagesFromGallery = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            rawImageUris.clear()
            croppedImageUris.clear()
            rawImageUris.addAll(uris)
            currentCropIndex = 0
            startCropNextImage()
        }
    }

    // 2. Sequential cropper activity result launcher
    private val cropImageLauncher = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            result.uriContent?.let { croppedUri ->
                val savedPath = saveImageToInternalStorage(croppedUri)
                if (savedPath != null) {
                    croppedImageUris.add(Uri.fromFile(File(savedPath)))
                } else {
                    croppedImageUris.add(croppedUri)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Cropping skipped for image ${currentCropIndex + 1}", Toast.LENGTH_SHORT).show()
        }

        currentCropIndex++
        if (currentCropIndex < rawImageUris.size) {
            startCropNextImage()
        } else {
            if (croppedImageUris.isNotEmpty()) {
                processMultipleImagesWithOcr(croppedImageUris, "Gallery Note")
            } else {
                hideLoadingDialog()
            }
        }
    }

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission denied.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan, container, false)

        viewFinder = view.findViewById(R.id.viewFinder)
        tvInstruction = view.findViewById(R.id.tvInstruction)
        val btnCapture = view.findViewById<Button>(R.id.btnCapture)
        val btnGallery = view.findViewById<View>(R.id.btnGallery)
        val btnFlash = view.findViewById<ImageButton>(R.id.btnFlash)

        checkAndStartCamera()

        btnCapture.setOnAnimatedClickListener {
            takePhoto()
        }

        btnGallery.setOnAnimatedClickListener {
            selectMultipleImagesFromGallery.launch("image/*")
        }

        btnFlash.setOnClickListener {
            if (camera?.cameraInfo?.hasFlashUnit() == true) {
                isTorchOn = !isTorchOn
                camera?.cameraControl?.enableTorch(isTorchOn)

                val statusText = if (isTorchOn) "Flashlight ON" else "Flashlight OFF"
                Toast.makeText(requireContext(), statusText, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Flash unavailable on this device", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun startCropNextImage() {
        if (currentCropIndex < rawImageUris.size) {
            val uriToCrop = rawImageUris[currentCropIndex]
            val cropOptions = CropImageContractOptions(
                uri = uriToCrop,
                cropImageOptions = CropImageOptions().apply {
                    guidelines = CropImageView.Guidelines.ON
                    activityTitle = "Crop Image (${currentCropIndex + 1}/${rawImageUris.size})"
                    cropMenuCropButtonTitle = if (currentCropIndex == rawImageUris.size - 1) "Done" else "Next"
                    allowRotation = true
                    allowFlipping = true
                }
            )
            cropImageLauncher.launch(cropOptions)
        }
    }

    @Suppress("unused")
    fun updateInstruction(message: String) {
        tvInstruction.text = message
    }

    private fun checkAndStartCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(requireContext(), "Failed to start camera: ${exc.message}", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        scanStartTime = System.currentTimeMillis()
        showLoadingDialog("Scanning process...")

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "NOTE2SNAP_$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/NOTE2SNAP")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    hideLoadingDialog()
                    Toast.makeText(requireContext(), "Photo capture failed: ${exc.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri
                    if (savedUri != null) {
                        processMultipleImagesWithOcr(listOf(savedUri), "Scanned Note")
                    } else {
                        hideLoadingDialog()
                    }
                }
            }
        )
    }

    private fun preprocessBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmpGrayscale)
        val paint = Paint()

        val grayscaleMatrix = ColorMatrix().apply {
            setSaturation(0f)
        }

        val contrast = 1.3f
        val translate = (-0.5f * contrast + 0.5f) * 255f
        val contrastMatrix = ColorMatrix(
            floatArrayOf(
                contrast, 0f, 0f, 0f, translate,
                0f, contrast, 0f, 0f, translate,
                0f, 0f, contrast, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        )

        grayscaleMatrix.postConcat(contrastMatrix)
        paint.colorFilter = ColorMatrixColorFilter(grayscaleMatrix)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return bmpGrayscale
    }

    private fun processMultipleImagesWithOcr(imageUris: List<Uri>, fallbackTitle: String) {
        scanStartTime = System.currentTimeMillis()
        showLoadingDialog("Processing ${imageUris.size} photo(s)...")

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val pageContents = mutableListOf<String>()
                var extractedTitle = ""
                val primaryImagePath = imageUris.firstOrNull()?.path ?: imageUris.firstOrNull()?.toString() ?: ""

                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

                for ((index, uri) in imageUris.withIndex()) {
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val originalBitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    if (originalBitmap != null) {
                        val processedBitmap = preprocessBitmap(originalBitmap)
                        val inputImage = InputImage.fromBitmap(processedBitmap, 0)

                        val visionText = Tasks.await(recognizer.process(inputImage))
                        val structuredNote = WhiteboardRuleEngine.process(visionText)

                        if (extractedTitle.isBlank() && structuredNote.title.isNotBlank() && structuredNote.title != "Untitled Scan") {
                            extractedTitle = structuredNote.title
                        }

                        val pageText = structuredNote.blocks.joinToString("<br/>") { it.formattedText }
                        if (pageText.isNotBlank()) {
                            if (imageUris.size > 1) {
                                pageContents.add("<b>--- Page ${index + 1} ---</b><br/>$pageText")
                            } else {
                                pageContents.add(pageText)
                            }
                        }
                    }
                }

                val finalTitle = extractedTitle.ifBlank { fallbackTitle }
                val formattedContent = pageContents.joinToString("<br/><br/>")

                saveNoteToDatabase(finalTitle, formattedContent, primaryImagePath)
                saveScanHistory(finalTitle, primaryImagePath)

                val elapsedTime = System.currentTimeMillis() - scanStartTime
                val remainingDelay = (5000L - elapsedTime).coerceAtLeast(0L)
                delay(remainingDelay.milliseconds)

                withContext(Dispatchers.Main) {
                    hideLoadingDialog()

                    val intent = Intent(requireContext(), PdfViewerActivity::class.java).apply {
                        putExtra("TITLE", finalTitle)
                        putExtra("CONTENT", formattedContent)
                        putExtra("IMAGE_PATH", primaryImagePath)
                    }
                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    hideLoadingDialog()
                    Toast.makeText(requireContext(), "Processing failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoadingDialog(message: String) {
        if (loadingDialog == null) {
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null)
            dialogView.findViewById<TextView>(R.id.tvLoadingMessage).text = message

            loadingDialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create().apply {
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
        } else {
            loadingDialog?.findViewById<TextView>(R.id.tvLoadingMessage)?.text = message
        }
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val fileName = "gallery_${System.currentTimeMillis()}.jpg"
            val file = File(requireContext().filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun saveNoteToDatabase(title: String, content: String, imagePath: String) {
        val currentDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        val newNote = Note(
            title = title,
            content = content,
            imagePath = imagePath,
            dateEdited = currentDate
        )
        AppDatabase.getDatabase(requireContext()).appDao().insertNote(newNote)
    }

    private suspend fun saveScanHistory(title: String, imagePath: String) {
        val currentDate = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date())
        val historyItem = ScanHistory(
            title = title,
            date = currentDate,
            imagePath = imagePath
        )
        AppDatabase.getDatabase(requireContext()).appDao().insertScanHistory(historyItem)
    }
}