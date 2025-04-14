package com.example.quickrecipe.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.quickrecipe.model.Recipe
import com.example.quickrecipe.ui.screens.SavedRecipe
import java.util.Date

/**
 * Singleton repository for managing favorite recipes
 */
object FavoritesRepository {
    // Observable list of favorite recipes
    private val favorites = mutableStateListOf<SavedRecipe>()
    
    /**
     * Add a recipe to favorites
     */
    fun addToFavorites(recipe: Recipe) {
        if (!isRecipeFavorite(recipe.id)) {
            favorites.add(SavedRecipe(recipe, Date()))
        }
    }
    
    /**
     * Remove a recipe from favorites
     */
    fun removeFromFavorites(recipeId: Int) {
        favorites.removeAll { it.recipe.id == recipeId }
    }
    
    /**
     * Toggle the favorite status of a recipe
     */
    fun toggleFavorite(recipe: Recipe): Boolean {
        return if (isRecipeFavorite(recipe.id)) {
            removeFromFavorites(recipe.id)
            false
        } else {
            addToFavorites(recipe)
            true
        }
    }
    
    /**
     * Check if a recipe is in favorites
     */
    fun isRecipeFavorite(recipeId: Int): Boolean {
        return favorites.any { it.recipe.id == recipeId }
    }
    
    /**
     * Get all favorite recipes
     */
    fun getAllFavorites(): List<SavedRecipe> {
        return favorites.toList()
    }
    
    /**
     * Get count of favorite recipes
     */
    fun getFavoritesCount(): Int {
        return favorites.size
    }
} 