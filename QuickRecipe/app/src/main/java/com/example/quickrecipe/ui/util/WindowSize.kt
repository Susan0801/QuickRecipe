package com.example.quickrecipe.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,    // Most phones in portrait mode
    MEDIUM,     // Most phones in landscape mode and tablets in portrait mode
    EXPANDED    // Tablets in landscape mode
}

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    return when {
        screenWidth < 600.dp -> WindowSize.COMPACT
        screenWidth < 840.dp -> WindowSize.MEDIUM
        else -> WindowSize.EXPANDED
    }
}

// Helper function to get responsive padding based on window size
@Composable
fun getResponsivePadding(windowSize: WindowSize) = when (windowSize) {
    WindowSize.COMPACT -> 16.dp
    WindowSize.MEDIUM -> 24.dp
    WindowSize.EXPANDED -> 32.dp
}

// Helper function to get responsive font size based on window size
@Composable
fun getResponsiveFontSize(windowSize: WindowSize, baseSize: Int) = when (windowSize) {
    WindowSize.COMPACT -> baseSize
    WindowSize.MEDIUM -> (baseSize * 1.2).toInt()
    WindowSize.EXPANDED -> (baseSize * 1.4).toInt()
} 