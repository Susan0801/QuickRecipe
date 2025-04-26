package com.example.quickrecipe.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * A single composable that covers both Login and Register screens.
 *
 * @param isLogin     – `true` → Login, `false` → Register
 * @param vm          – DI/preview-friendly ViewModel
 * @param onAuthResult – host callback (e.g. MainActivity) gets every state change
 */
@Composable
fun AuthScreen(
    isLogin: Boolean,
    vm: AuthViewModel = viewModel(),
    onAuthResult: (AuthState) -> Unit = {}
) {
    // observe ViewModel state
    val state by vm.state.collectAsState()

    // notify host whenever state changes
    LaunchedEffect(state) { onAuthResult(state) }

    // local text-field states
    var email by remember { mutableStateOf("") }
    var pass  by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /* ---------- Title ---------- */
        Text(
            text   = if (isLogin) "Login" else "Register",
            style  = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(8.dp))

        /* ---------- Fields ---------- */
        OutlinedTextField(
            value         = email,
            onValueChange = { email = it },
            label         = { Text("Email") },
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value         = pass,
            onValueChange = { pass = it },
            label         = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine    = true,
            modifier      = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- Action button ---------- */
        Button(
            onClick = {
                if (isLogin) vm.login(email.trim(), pass)
                else        vm.register(email.trim(), pass)
            },
            enabled = state != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLogin) "Login" else "Sign up")
        }

        Spacer(Modifier.height(12.dp))

        /* ---------- Status feedback ---------- */
        when (state) {
            is AuthState.Error ->
                Text(
                    text  = (state as AuthState.Error).msg,
                    color = MaterialTheme.colorScheme.error
                )
            AuthState.Loading  -> CircularProgressIndicator()
            AuthState.Success  -> Text(
                text  = "✓ Success!",
                color = MaterialTheme.colorScheme.primary
            )
            else -> {}
        }

        /* ---------- Switch link ---------- */
        TextButton(
            onClick = {
                // ask the host NavController to swap screens
                onAuthResult(AuthState.Error("switch"))
            }
        ) {
            Text(
                if (isLogin) "No account?  Register"
                else         "Already have an account?  Login"
            )
        }
    }
}

/* ---------- Previews ---------- */

@Preview(showBackground = true)
@Composable fun LoginPreview()  = AuthScreen(isLogin = true)

@Preview(showBackground = true)
@Composable fun RegisterPreview() = AuthScreen(isLogin = false)

