package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickrecipe.data.repository.FavoritesRepository
import com.example.quickrecipe.model.MockRecipe
import com.example.quickrecipe.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onBackPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Get the recipe by ID
    val recipe = MockRecipe.getRecipeById(recipeId) ?: return
    
    // State for favorite button - check initial state from repository
    var isFavorite by remember { mutableStateOf(FavoritesRepository.isRecipeFavorite(recipeId)) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title */ },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Toggle favorite and update the UI state
                            isFavorite = FavoritesRepository.toggleFavorite(recipe)
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) Color.Red else LocalContentColor.current
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Recipe Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                recipe.imageUrl?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
            }
            
            // Recipe Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = recipe.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                // Cuisine and Time
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "⏱️ ",
                        fontSize = 16.sp,
                        color = Color.Gray,
                    )
                    Text(
                        text = "${recipe.prepTime + recipe.cookingTime} mins",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CuisineChip(cuisine = recipe.cuisineType)
                }
                
                // Divider
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Ingredients Section
                Text(
                    text = "Ingredients",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                recipe.ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "• ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = ingredient)
                    }
                }
                
                // Divider
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                
                // Instructions Section
                Text(
                    text = "Instructions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                recipe.instructions.forEachIndexed { index, instruction ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "${index + 1}. ",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = instruction)
                    }
                }
                
                // Cooking Info
                Spacer(modifier = Modifier.height(16.dp))
                CookingInfoCard(
                    prepTime = recipe.prepTime,
                    cookingTime = recipe.cookingTime,
                    totalTime = recipe.prepTime + recipe.cookingTime
                )
                
                // Dietary Info if available
                if (recipe.dietaryPrefs.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DietaryInfoSection(dietaryPrefs = recipe.dietaryPrefs)
                }
                
                // Bottom spacing
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CuisineChip(cuisine: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEEEEEE))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = cuisine,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun CookingInfoCard(
    prepTime: Int,
    cookingTime: Int,
    totalTime: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimeInfoColumn(label = "Prep Time", time = "$prepTime min")
            VerticalDivider()
            TimeInfoColumn(label = "Cooking", time = "$cookingTime min")
            VerticalDivider()
            TimeInfoColumn(label = "Total", time = "$totalTime min")
        }
    }
}

@Composable
fun TimeInfoColumn(label: String, time: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Text(
            text = time,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Color.LightGray)
    )
}

@Composable
fun DietaryInfoSection(dietaryPrefs: List<String>) {
    Column {
        Text(
            text = "Dietary",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            dietaryPrefs.forEach { pref ->
                DietaryChip(pref = pref)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun DietaryChip(pref: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF3DB489))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = pref.replaceFirstChar { it.uppercase() },
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun RecipeDetailScreenPreview() {
    // Get the first recipe for preview
    val recipeId = MockRecipe.getAllRecipes().firstOrNull()?.id ?: 1
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        RecipeDetailScreen(recipeId = recipeId)
    }
}

