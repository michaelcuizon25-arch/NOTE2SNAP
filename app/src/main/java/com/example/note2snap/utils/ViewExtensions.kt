package com.example.note2snap.utils

import android.view.MotionEvent
import android.view.View

fun View.setOnAnimatedClickListener(onClick: () -> Unit) {
    this.setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                view.animate()
                    .scaleX(0.94f)
                    .scaleY(0.94f)
                    .setDuration(100)
                    .start()
            }
            MotionEvent.ACTION_UP -> {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(100)
                    .withEndAction { onClick() }
                    .start()
            }
            MotionEvent.ACTION_CANCEL -> {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(100)
                    .start()
            }
        }
        true
    }
}