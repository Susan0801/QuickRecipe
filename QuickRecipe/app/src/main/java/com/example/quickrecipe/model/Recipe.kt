package com.example.quickrecipe.model

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val cuisineType: String,
    val cookingTime: Int, // in minutes
    val prepTime: Int, // in minutes
    val totalTime: Int = prepTime + cookingTime,
    val rating: Double = 0.0,
    val numRatings: Int = 0,
    val isFavorite: Boolean = false,
    val isSaved: Boolean = false,
    val isCooked: Boolean = false,
    val difficulty: Difficulty,
    val dietaryPrefs: List<String>,
    val imageUrl: String? = null
)