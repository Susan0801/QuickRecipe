package com.example.quickrecipe.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import com.example.quickrecipe.data.entity.NavigationItem
import com.example.quickrecipe.data.repository.FavoritesRepository
import com.example.quickrecipe.model.Recipe
import com.example.quickrecipe.ui.components.QuickRecipeTopAppBar

@Composable
fun MainFrame() {
    val navigationItems = listOf(
        NavigationItem("Kitchen", Icons.Filled.Home),
        NavigationItem("Recipe", Icons.Filled.Menu),
        NavigationItem("Favorite", Icons.Filled.Favorite),
        NavigationItem("Settings", Icons.Filled.Settings)
    )

    val currentNavigationIndex = remember { mutableIntStateOf(0) }
    
    // State to track if we're viewing a recipe detail
    var selectedRecipeId by remember { mutableStateOf<Int?>(null) }
    
    // Track favorites count for badge
    val favoritesCount = remember { mutableStateOf(FavoritesRepository.getFavoritesCount()) }
    
    // Update favorites count whenever it changes
    LaunchedEffect(Unit) {
        favoritesCount.value = FavoritesRepository.getFavoritesCount()
    }
    
    // List of available cuisines
    val cuisines = listOf("All", "Italian", "Mexican", "Chinese", "Indian", "Thai")
    // Track selected cuisine
    val selectedCuisine = remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            // Only show TopAppBar when not on Recipes screen, Favorites screen, 
            // Settings screen, and not viewing recipe detail
            if (currentNavigationIndex.value != 1 && 
                currentNavigationIndex.value != 2 && 
                currentNavigationIndex.value != 3 && 
                selectedRecipeId == null) {
                QuickRecipeTopAppBar(
                    cuisines = cuisines,
                    selectedCuisine = selectedCuisine.value,
                    onCuisineSelected = { selectedCuisine.value = it }
                )
            }
        },
        bottomBar = {
            // Only show bottom navigation when not viewing recipe detail
            if (selectedRecipeId == null) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    navigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                if (index == 2 && FavoritesRepository.getFavoritesCount() > 0) {
                                    // Show badge for favorites
                                    BadgedBox(
                                        badge = {
                                            if (FavoritesRepository.getFavoritesCount() > 0) {
                                                Badge {
                                                    Text(FavoritesRepository.getFavoritesCount().toString())
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(item.icon, contentDescription = item.title)
                                    }
                                } else {
                                    Icon(item.icon, contentDescription = item.title)
                                }
                            },
                            label = { Text(item.title) },
                            selected = currentNavigationIndex.value == index,
                            onClick = { 
                                currentNavigationIndex.value = index
                                // When entering Favorites screen, refresh the favorites count
                                if (index == 2) {
                                    favoritesCount.value = FavoritesRepository.getFavoritesCount()
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // If we're viewing a recipe detail, show that instead of the main screens
        if (selectedRecipeId != null) {
            RecipeDetailScreen(
                recipeId = selectedRecipeId!!,
                onBackPressed = { 
                    selectedRecipeId = null 
                    // Update favorites count when returning from detail screen
                    favoritesCount.value = FavoritesRepository.getFavoritesCount()
                }
            )
        } else {
            // Otherwise show the main navigation screens
            when (currentNavigationIndex.value) {
                0 -> {
                    // Kitchen Screen
                    Text(
                        text = "Kitchen Screen",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(innerPadding).padding(16.dp)
                    )
                }
                1 -> {
                    // Recipes Screen
                    RecipesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRecipeClick = { recipe ->
                            selectedRecipeId = recipe.id
                        }
                    )
                }
                2 -> {
                    // Favorites Screen
                    FavoritesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRecipeClick = { recipe ->
                            selectedRecipeId = recipe.id
                        },
                        onDiscoverRecipesClick = {
                            // Navigate to Recipes tab when "Discover Recipes" is clicked
                            currentNavigationIndex.value = 1
                        }
                    )
                }
                3 -> {
                    // Settings Screen
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
