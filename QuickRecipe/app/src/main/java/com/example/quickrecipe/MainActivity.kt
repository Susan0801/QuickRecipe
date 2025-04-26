package com.example.quickrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.quickrecipe.ui.screens.MainFrame
import com.example.quickrecipe.ui.theme.QuickRecipeTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickRecipeTheme {
                MainFrame()

            }
        }
    }
}
