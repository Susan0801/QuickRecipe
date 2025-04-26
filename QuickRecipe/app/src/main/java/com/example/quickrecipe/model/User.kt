package com.example.quickrecipe.model

/**
 * Represents a user in the application
 */
data class User(
    val id: String,
    val email: String,
    val name: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val recipesCreated: Int = 0
)