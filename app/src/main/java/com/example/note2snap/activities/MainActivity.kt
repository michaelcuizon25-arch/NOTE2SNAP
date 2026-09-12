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
    private var currentTabIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Only trigger initial theme check on cold start to prevent recreation loops
        if (savedInstanceState == null) {
            val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            val isSystemDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            val isDarkModeSaved = sharedPref.getBoolean("DARK_MODE", isSystemDark)
            val targetMode = if (isDarkModeSaved) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

            if (AppCompatDelegate.getDefaultNightMode() != targetMode) {
                AppCompatDelegate.setDefaultNightMode(targetMode)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigation)

        if (savedInstanceState != null) {
            currentTabIndex = savedInstanceState.getInt("SAVED_TAB_INDEX", 4)
        } else {
            currentTabIndex = 0
            loadFragment(HomeFragment())
        }

        // Set active item quiet state BEFORE attaching listener
        bottomNav.selectedItemId = getMenuIdForIndex(currentTabIndex)
        setupNavigationListener()

        // Safely animate bottom navigation curve after view layout completes
        bottomNav.post {
            try {
                bottomNav.animateToTab(currentTabIndex, totalTabs = 5)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupNavigationListener() {
        bottomNav.setOnItemSelectedListener { item ->
            val (fragmentSupplier, tabIndex) = when (item.itemId) {
                R.id.nav_home -> Pair({ HomeFragment() }, 0)
                R.id.nav_notes -> Pair({ NotesFragment() }, 1)
                R.id.nav_scan -> Pair({ ScanFragment() }, 2)
                R.id.nav_history -> Pair({ HistoryFragment() }, 3)
                R.id.nav_settings -> Pair({ SettingsFragment() }, 4)
                else -> Pair({ HomeFragment() }, 0)
            }

            if (currentTabIndex != tabIndex) {
                currentTabIndex = tabIndex
                bottomNav.animateToTab(tabIndex, totalTabs = 5)
                loadFragment(fragmentSupplier())
            }
            true
        }
    }

    private fun getMenuIdForIndex(index: Int): Int {
        return when (index) {
            0 -> R.id.nav_home
            1 -> R.id.nav_notes
            2 -> R.id.nav_scan
            3 -> R.id.nav_history
            4 -> R.id.nav_settings
            else -> R.id.nav_home
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SAVED_TAB_INDEX", currentTabIndex)
    }

    fun loadFragment(fragment: Fragment) {
        if (supportFragmentManager.isStateSaved) return

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun selectTab(itemId: Int) {
        bottomNav.selectedItemId = itemId
    }
}