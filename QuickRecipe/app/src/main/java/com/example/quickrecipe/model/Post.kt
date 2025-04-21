package com.example.quickrecipe.model

import java.util.Date

/**
 * Model class representing a user post
 */
data class Post(
    val id: Int,
    val userId: String,
    val username: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val timestamp: Date,
    val likes: Int = 0,
    val comments: Int = 0,
    val recipeId: Int? = null, // Optional reference to a recipe
    val isNewRecipe: Boolean = false, // Indicates if this post is also creating a new recipe
    val tags: List<String> = emptyList()
) 