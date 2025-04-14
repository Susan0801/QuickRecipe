package com.example.quickrecipe.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * Controller for app-wide theme settings
 */
object ThemeController {
    // State for dark mode setting
    private val _isDarkMode = mutableStateOf(false)
    
    /**
     * Get the current dark mode state
     */
    val isDarkMode: Boolean
        get() = _isDarkMode.value
    
    /**
     * Toggle dark mode on/off
     */
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }
    
    /**
     * Set dark mode to a specific state
     */
    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }
}

/**
 * Composable function to use the theme controller state in a composable
 */
@Composable
fun rememberThemeState(): Pair<Boolean, (Boolean) -> Unit> {
    var isDarkMode by remember { mutableStateOf(ThemeController.isDarkMode) }
    
    val onThemeChanged: (Boolean) -> Unit = { enabled ->
        ThemeController.setDarkMode(enabled)
        isDarkMode = enabled
    }
    
    return Pair(isDarkMode, onThemeChanged)
} 