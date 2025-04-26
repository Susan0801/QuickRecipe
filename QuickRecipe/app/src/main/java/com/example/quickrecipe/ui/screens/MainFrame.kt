package com.example.quickrecipe.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalContext
import com.example.quickrecipe.data.entity.NavigationItem
import com.example.quickrecipe.data.repository.FavoritesRepository
import com.example.quickrecipe.data.repository.UserRepository
import com.example.quickrecipe.model.Post
import com.example.quickrecipe.model.Recipe
import com.example.quickrecipe.ui.components.QuickRecipeTopAppBar
import com.example.quickrecipe.ui.components.QuickRecipeBottomNavBar

@Composable
fun MainFrame() {
    val currentNavigationIndex = remember { mutableIntStateOf(0) }
    
    // State to track if we're viewing a recipe detail
    var selectedRecipeId by remember { mutableStateOf<Int?>(null) }
    
    // State to track if we're viewing a post detail
    var selectedPost by remember { mutableStateOf<Post?>(null) }
    
    // Track favorites count for badge
    val favoritesCount = remember { mutableStateOf(FavoritesRepository.getFavoritesCount()) }
    
    // State to track if we're viewing settings
    var showSettings by remember { mutableStateOf(false) }
    
    // State to track if we're viewing login screen
    var showLoginScreen by remember { mutableStateOf(false) }
    
    // State to track if we're viewing register screen
    var showRegisterScreen by remember { mutableStateOf(false) }
    
    // Get current user
    val currentUser by UserRepository.currentUser
    
    // Get context for saving user data
    val context = LocalContext.current
    
    // Initialize UserRepository
    LaunchedEffect(Unit) {
        UserRepository.initialize(context)
    }
    
    // Update favorites count whenever it changes
    LaunchedEffect(Unit) {
        favoritesCount.value = FavoritesRepository.getFavoritesCount()
    }

    Scaffold(
        topBar = {
            // Only show TopAppBar when not viewing detail screens, settings, login, or register
            if (selectedRecipeId == null && !showSettings && selectedPost == null && 
                !showLoginScreen && !showRegisterScreen) {
                QuickRecipeTopAppBar(
                    onSettingsClick = { showSettings = true },
                    onProfileClick = { 
                        if (currentUser == null) {
                            showLoginScreen = true
                        } else {
                            // TODO: Show profile screen
                            // For now, just show login screen again to handle logout
                            showLoginScreen = true
                        }
                    }
                )
            }
        },
        bottomBar = {
            // Only show bottom navigation when not viewing detail screens, settings, login, or register
            if (selectedRecipeId == null && !showSettings && selectedPost == null && 
                !showLoginScreen && !showRegisterScreen) {
                QuickRecipeBottomNavBar(
                    currentIndex = currentNavigationIndex.intValue,
                    onTabSelected = { index -> 
                        currentNavigationIndex.intValue = index
                        // When entering Favorites screen, refresh the favorites count
                        if (index == 4) {
                            favoritesCount.value = FavoritesRepository.getFavoritesCount()
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // If we're viewing the register screen
        if (showRegisterScreen) {
            RegisterScreen(
                modifier = Modifier.padding(innerPadding),
                onBackPressed = { showRegisterScreen = false },
                onRegisterSuccess = {
                    // Save user login data
                    currentUser?.let { user ->
                        UserRepository.saveUserLogin(context, user)
                    }
                    showRegisterScreen = false
                }
            )
        }
        // If we're viewing the login screen
        else if (showLoginScreen) {
            LoginScreen(
                modifier = Modifier.padding(innerPadding),
                onBackPressed = { showLoginScreen = false },
                onLoginSuccess = { 
                    // Save user login data
                    currentUser?.let { user ->
                        UserRepository.saveUserLogin(context, user)
                    }
                    showLoginScreen = false 
                },
                onRegisterClick = {
                    showLoginScreen = false
                    showRegisterScreen = true
                }
            )
        }
        // If we're viewing settings, show the settings screen
        else if (showSettings) {
            SettingsScreen(
                modifier = Modifier.padding(innerPadding),
                onBackPressed = { showSettings = false }
            )
        } 
        // If we're viewing a recipe detail, show that screen
        else if (selectedRecipeId != null) {
            RecipeDetailScreen(
                recipeId = selectedRecipeId!!,
                onBackPressed = { 
                    selectedRecipeId = null 
                    // Update favorites count when returning from detail screen
                    favoritesCount.value = FavoritesRepository.getFavoritesCount()
                }
            )
        }
        // If we're viewing a post detail, show that screen
        else if (selectedPost != null) {
            // This would be the post detail screen (future implementation)
            // For now, we'll just set it back to null to return to the posts list
            selectedPost = null
        }
        else {
            // Otherwise show the main navigation screens
            when (currentNavigationIndex.intValue) {
                0 -> {
                    // Kitchen Screen
                    var showRecipeResults by remember { mutableStateOf(false) }
                    var selectedIngredients by remember { mutableStateOf(listOf<String>()) }

                    if (showRecipeResults) {
                        FindRecipeResultListScreen(
                            ingredients = selectedIngredients,
                            onBackPressed = { showRecipeResults = false },
                            onRecipeClick = { recipe ->
                                selectedRecipeId = recipe.id
                            }
                        )
                    } else {
                        KitchenScreen(
                            modifier = Modifier.padding(innerPadding),
                            onFindRecipes = { ingredients ->
                                selectedIngredients = ingredients
                                showRecipeResults = true
                            }
                        )
                    }
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
                    // Create Recipe Screen
                    CreateRecipeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRecipeCreated = { recipe ->
                            // Navigate to the post screen after creating a recipe
                            currentNavigationIndex.intValue = 3 // Post screen index
                        },
                        onBackPressed = {
                            // Go back to recipe list when cancelled
                            currentNavigationIndex.intValue = 1
                        }
                    )
                }
                3 -> {
                    // Posts Screen
                    PostScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRecipeClick = { recipe ->
                            selectedRecipeId = recipe.id
                        },
                        onPostClick = { post ->
                            selectedPost = post
                        }
                    )
                }
                4 -> {
                    // Favorites Screen
                    FavoritesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRecipeClick = { recipe ->
                            selectedRecipeId = recipe.id
                        },
                        onDiscoverRecipesClick = {
                            // Navigate to Recipes tab when "Discover Recipes" is clicked
                            currentNavigationIndex.intValue = 1
                        }
                    )
                }
            }
        }
    }
}
