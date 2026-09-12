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
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f // Adds outline visibility on light backgrounds
    }

    private var currentCenterX = 0f
    private var targetCenterX = 0f
    private var animator: ValueAnimator? = null

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

        fillPaint.color = ContextCompat.getColor(context, R.color.card_bg)
        // Subtle top border color for light mode contrast
        borderPaint.color = ContextCompat.getColor(context, R.color.bottom_nav_border)

        val width = width.toFloat()
        val height = height.toFloat()

        val startX = currentCenterX - curveRadius
        val endX = currentCenterX + curveRadius

        path.moveTo(0f, curveHeight)
        path.lineTo(startX, curveHeight)

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

        // Draw background fill and top border outline
        canvas.drawPath(path, fillPaint)
        canvas.drawPath(path, borderPaint)

        super.onDraw(canvas)
    }
}