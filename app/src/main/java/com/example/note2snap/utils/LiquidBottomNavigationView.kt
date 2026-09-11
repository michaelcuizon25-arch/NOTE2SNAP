package com.example.note2snap.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.example.note2snap.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class LiquidBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var currentCenterX = 0f
    private var targetCenterX = 0f
    private var animator: ValueAnimator? = null

    // Curve dimensions
    private val curveHeight = 32f
    private val curveRadius = 90f

    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (currentCenterX == 0f) {
            currentCenterX = w / 2f
        }
    }

    fun animateToTab(tabIndex: Int, totalTabs: Int = 5) {
        val tabWidth = width.toFloat() / totalTabs
        targetCenterX = (tabWidth * tabIndex) + (tabWidth / 2f)

        animator?.cancel()
        animator = ValueAnimator.ofFloat(currentCenterX, targetCenterX).apply {
            duration = 350
            interpolator = DecelerateInterpolator()
            addUpdateListener { anim ->
                currentCenterX = anim.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        path.reset()

        // Dynamically fetch background color for active theme
        paint.color = ContextCompat.getColor(context, R.color.card_bg)

        val width = width.toFloat()
        val height = height.toFloat()

        // Control points for the liquid bezier curve
        val startX = currentCenterX - curveRadius
        val endX = currentCenterX + curveRadius

        path.moveTo(0f, curveHeight)
        path.lineTo(startX, curveHeight)

        // Smooth liquid curve around active tab
        path.cubicTo(
            startX + (curveRadius / 2), curveHeight,
            currentCenterX - (curveRadius / 2), 0f,
            currentCenterX, 0f
        )
        path.cubicTo(
            currentCenterX + (curveRadius / 2), 0f,
            endX - (curveRadius / 2), curveHeight,
            endX, curveHeight
        )

        path.lineTo(width, curveHeight)
        path.lineTo(width, height)
        path.lineTo(0f, height)
        path.close()

        canvas.drawPath(path, paint)
        super.onDraw(canvas)
    }
}