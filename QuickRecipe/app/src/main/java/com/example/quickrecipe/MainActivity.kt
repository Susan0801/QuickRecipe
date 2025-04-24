package com.example.quickrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.*
import com.example.quickrecipe.ui.AuthScreen         // ← add this file if you haven’t yet
import com.example.quickrecipe.ui.AuthState
import com.example.quickrecipe.ui.screens.MainFrame // ← your existing scaffold
import com.example.quickrecipe.ui.theme.QuickRecipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuickRecipeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val nav = rememberNavController()

                    NavHost(
                        navController = nav,
                        startDestination = "login"
                    ) {

                        composable("login") {
                            AuthScreen(isLogin = true) { state ->
                                when (state) {
                                    is AuthState.Success     -> nav.navigate("main")   /* login success */
                                    is AuthState.Error ->
                                        if (state.msg == "switch") nav.navigate("register")
                                    else -> {}
                                }
                            }
                        }
                        composable("register") {
                            AuthScreen(isLogin = false) { state ->
                                when (state) {
                                    is AuthState.Success     -> nav.navigate("main")   /* register success */
                                    is AuthState.Error ->
                                        if (state.msg == "switch") nav.navigate("login")
                                    else -> {}
                                }
                            }
                        }

                        composable("main") { MainFrame() }
                    }
                }
            }
        }
    }
}
