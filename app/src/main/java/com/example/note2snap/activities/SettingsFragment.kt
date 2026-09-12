package com.example.note2snap.activities

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.example.note2snap.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var isAnimating = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeToggle(view)
    }

    private fun setupThemeToggle(view: View) {
        val viewPillSelector = view.findViewById<View>(R.id.viewPillSelector)
        val btnDark = view.findViewById<LinearLayout>(R.id.btnDarkTheme)
        val btnLight = view.findViewById<LinearLayout>(R.id.btnLightTheme)
        val tvDark = view.findViewById<TextView>(R.id.tvDarkLabel)
        val tvLight = view.findViewById<TextView>(R.id.tvLightLabel)

        if (viewPillSelector == null || btnDark == null || btnLight == null || tvDark == null || tvLight == null) return

        val sharedPref = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isSystemDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val isDarkMode = sharedPref.getBoolean("DARK_MODE", isSystemDark)

        // Set initial pill position without playing animation
        viewPillSelector.doOnLayout {
            val targetX = if (isDarkMode) 0f else btnDark.width.toFloat()
            viewPillSelector.translationX = targetX
            updateTextColors(isDarkMode, tvDark, tvLight)
        }

        btnDark.setOnClickListener {
            val isCurrentlyDark = sharedPref.getBoolean("DARK_MODE", isSystemDark)
            if (!isCurrentlyDark && !isAnimating) {
                animatePill(viewPillSelector, 0f, true, sharedPref, tvDark, tvLight)
            }
        }

        btnLight.setOnClickListener {
            val isCurrentlyDark = sharedPref.getBoolean("DARK_MODE", isSystemDark)
            if (isCurrentlyDark && !isAnimating) {
                val targetX = btnDark.width.toFloat()
                animatePill(viewPillSelector, targetX, false, sharedPref, tvDark, tvLight)
            }
        }
    }

    private fun animatePill(
        selector: View,
        targetX: Float,
        setDark: Boolean,
        sharedPref: android.content.SharedPreferences,
        tvDark: TextView,
        tvLight: TextView
    ) {
        isAnimating = true
        updateTextColors(setDark, tvDark, tvLight)

        selector.animate()
            .translationX(targetX)
            .setDuration(220)
            .withEndAction {
                sharedPref.edit().putBoolean("DARK_MODE", setDark).commit()
                val targetMode = if (setDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(targetMode)
                isAnimating = false
            }
            .start()
    }

    private fun updateTextColors(isDark: Boolean, tvDark: TextView, tvLight: TextView) {
        if (isDark) {
            tvDark.setTextColor(Color.WHITE)
            tvLight.setTextColor(Color.parseColor("#9CA3AF"))
        } else {
            tvLight.setTextColor(Color.BLACK)
            tvDark.setTextColor(Color.parseColor("#6B7280"))
        }
    }
}