package com.example.quickrecipe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickrecipe.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    object Idle : AuthState
    object Loading : AuthState
    object Success : AuthState
    data class Error(val msg: String) : AuthState
}

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun login(email: String, pass: String)  = authOp { repo.login(email, pass) }
    fun register(email: String, pass: String) = authOp { repo.register(email, pass) }

    private fun authOp(block: suspend () -> Unit) = viewModelScope.launch {
        _state.value = AuthState.Loading
        runCatching { block() }
            .onSuccess { _state.value = AuthState.Success }
            .onFailure { _state.value = AuthState.Error(it.message ?: "Unknown") }
    }
}
