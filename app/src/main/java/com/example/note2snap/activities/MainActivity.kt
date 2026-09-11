package com.example.note2snap.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.note2snap.R
import com.example.note2snap.utils.LiquidBottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: LiquidBottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Read saved preference and set Night Mode BEFORE layout inflation
        val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val isSystemDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val isDarkModeSaved = sharedPref.getBoolean("DARK_MODE", isSystemDark)

        val targetMode = if (isDarkModeSaved) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        if (AppCompatDelegate.getDefaultNightMode() != targetMode) {
            AppCompatDelegate.setDefaultNightMode(targetMode)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            // Position initial liquid curve under Home (Index 0)
            bottomNav.post {
                bottomNav.animateToTab(0, totalTabs = 5)
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            val (fragment, tabIndex) = when (item.itemId) {
                R.id.nav_home -> Pair(HomeFragment(), 0)
                R.id.nav_notes -> Pair(NotesFragment(), 1)
                R.id.nav_scan -> Pair(ScanFragment(), 2)
                R.id.nav_history -> Pair(HistoryFragment(), 3)
                R.id.nav_settings -> Pair(SettingsFragment(), 4)
                else -> Pair(HomeFragment(), 0)
            }

            // Animate liquid wave to target tab position
            bottomNav.animateToTab(tabIndex, totalTabs = 5)
            loadFragment(fragment)
            true
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // Call this from child fragments to update the active tab and trigger liquid wave
    fun selectTab(itemId: Int) {
        bottomNav.selectedItemId = itemId
    }
}