package com.example.quickrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.quickrecipe.ui.screens.MainFrame
import com.example.quickrecipe.ui.theme.QuickRecipeTheme
import com.example.quickrecipe.data.repository.UserRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize UserRepository
        UserRepository.initialize(applicationContext)
        
        enableEdgeToEdge()
        setContent {
            QuickRecipeTheme {
                MainFrame()
            }
        }
    }
}
