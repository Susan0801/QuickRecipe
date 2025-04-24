package com.example.quickrecipe.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    val currentUser get() = auth.currentUser

    suspend fun register(email: String, pass: String) =
        auth.createUserWithEmailAndPassword(email, pass).await()

    suspend fun login(email: String, pass: String) =
        auth.signInWithEmailAndPassword(email, pass).await()

    fun logout() = auth.signOut()
}
