package com.example.quickrecipe.model

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val cuisineType: String,
    val cookingTime: Int, // in minutes
    val prepTime: Int, // in minutes
    val dietaryPrefs: List<String>,
    val imageUrl: String? = null
)