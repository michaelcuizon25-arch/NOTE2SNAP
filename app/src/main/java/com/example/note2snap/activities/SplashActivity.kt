package com.example.note2snap.activities

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.note2snap.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val vvLogo = findViewById<VideoView>(R.id.vvLogo)
        val videoPath = "android.resource://$packageName/${R.raw.ns}"

        // Set up and play MP4 video
        vvLogo.setVideoURI(Uri.parse(videoPath))

        vvLogo.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = false
            vvLogo.start()
        }

        // Navigate to MainActivity automatically when video completes
        vvLogo.setOnCompletionListener {
            navigateToMain()
        }

        startCameraLoadingAnimation(vvLogo)
    }

    private fun startCameraLoadingAnimation(vvLogo: VideoView) {
        val ivScanRing = findViewById<ImageView>(R.id.ivScanRing)
        val vScanBeam = findViewById<View>(R.id.vScanBeam)

        // 1. Endless camera focus ring rotation (360 degrees)
        ObjectAnimator.ofFloat(ivScanRing, "rotation", 0f, 360f).apply {
            duration = 2400
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        // 2. Scanner laser line bouncing up and down
        ObjectAnimator.ofFloat(vScanBeam, "translationY", -180f, 180f).apply {
            duration = 1200
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        // 3. Subtle lens pulse applied directly to the video view
        ObjectAnimator.ofFloat(vvLogo, "scaleX", 0.94f, 1.06f).apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
        ObjectAnimator.ofFloat(vvLogo, "scaleY", 0.94f, 1.06f).apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }

    private fun navigateToMain() {
        if (!isFinishing) {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}