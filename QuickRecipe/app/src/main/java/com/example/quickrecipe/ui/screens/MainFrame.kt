package com.example.quickrecipe.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
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
import com.example.quickrecipe.ui.util.WindowSize
import com.example.quickrecipe.ui.util.rememberWindowSize
import com.example.quickrecipe.ui.util.getResponsivePadding

@Composable
fun MainFrame() {
    val currentNavigationIndex = remember { mutableIntStateOf(0) }
    val windowSize = rememberWindowSize()
    val padding = getResponsivePadding(windowSize)
    
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
    
    // State to track if we're viewing profile screen
    var showProfileScreen by remember { mutableStateOf(false) }
    
    // Track where the user is coming from when logging in
    var loginNavigationSource by remember { mutableStateOf(0) }
    
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (selectedRecipeId == null && !showSettings && selectedPost == null && 
                !showLoginScreen && !showRegisterScreen && !showProfileScreen) {
                QuickRecipeTopAppBar(
                    onSettingsClick = { showSettings = true },
                    onProfileClick = { 
                        if (currentUser == null) {
                            showLoginScreen = true
                        } else {
                            showProfileScreen = true
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (selectedRecipeId == null && !showSettings && selectedPost == null && 
                !showLoginScreen && !showRegisterScreen) {
                QuickRecipeBottomNavBar(
                    currentIndex = currentNavigationIndex.intValue,
                    onTabSelected = { index -> 
                        currentNavigationIndex.intValue = index
                        if (index == 4) {
                            favoritesCount.value = FavoritesRepository.getFavoritesCount()
                        }
                        if (showProfileScreen) {
                            showProfileScreen = false
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // If we're viewing the register screen
            if (showRegisterScreen) {
                RegisterScreen(
                    modifier = Modifier.fillMaxSize(),
                    onBackPressed = { showRegisterScreen = false },
                    onRegisterSuccess = {
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
                    modifier = Modifier.fillMaxSize(),
                    onBackPressed = { 
                        showLoginScreen = false
                        if (loginNavigationSource == 2) {
                            currentNavigationIndex.intValue = 1
                        }
                    },
                    onLoginSuccess = { 
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
            // If we're viewing the profile screen
            else if (showProfileScreen && currentUser != null) {
                ProfileScreen(
                    currentUser = currentUser!!,
                    modifier = Modifier.fillMaxSize(),
                    onBackPressed = { showProfileScreen = false },
                    onLogout = {
                        showProfileScreen = false
                        showLoginScreen = true
                    },
                    currentNavigationIndex = currentNavigationIndex.intValue,
                    onNavigate = { index ->
                        currentNavigationIndex.intValue = index
                        showProfileScreen = false
                    }
                )
            }
            // If we're viewing settings
            else if (showSettings) {
                SettingsScreen(
                    modifier = Modifier.fillMaxSize(),
                    onBackPressed = { showSettings = false }
                )
            }
            // If we're viewing a recipe detail
            else if (selectedRecipeId != null) {
                RecipeDetailScreen(
                    recipeId = selectedRecipeId!!,
                    onBackPressed = { 
                        selectedRecipeId = null 
                        favoritesCount.value = FavoritesRepository.getFavoritesCount()
                    }
                )
            }
            // Otherwise show the main navigation screens
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = padding)
                ) {
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
                                    modifier = Modifier.fillMaxSize(),
                                    onFindRecipes = { ingredients ->
                                        selectedIngredients = ingredients
                                        showRecipeResults = true
                                    }
                                )
                            }
                        }
                        1 -> {
                            RecipesScreen(
                                modifier = Modifier.fillMaxSize(),
                                onRecipeClick = { recipe ->
                                    selectedRecipeId = recipe.id
                                }
                            )
                        }
                        2 -> {
                            if (currentUser == null) {
                                loginNavigationSource = 2
                                showLoginScreen = true
                            } else {
                                CreateRecipeScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onRecipeCreated = { recipe ->
                                        currentNavigationIndex.intValue = 3
                                    },
                                    onBackPressed = {
                                        currentNavigationIndex.intValue = 1
                                    },
                                    currentUser = currentUser!!
                                )
                            }
                        }
                        3 -> {
                            PostScreen(
                                modifier = Modifier.fillMaxSize(),
                                currentUser = currentUser,
                                onRecipeClick = { recipe ->
                                    selectedRecipeId = recipe.id
                                },
                                onPostClick = { post ->
                                    selectedPost = post
                                },
                                onLoginRequired = {
                                    loginNavigationSource = 3
                                    showLoginScreen = true
                                }
                            )
                        }
                        4 -> {
                            FavoritesScreen(
                                modifier = Modifier.fillMaxSize(),
                                onRecipeClick = { recipe ->
                                    selectedRecipeId = recipe.id
                                },
                                onDiscoverRecipesClick = {
                                    currentNavigationIndex.intValue = 1
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}