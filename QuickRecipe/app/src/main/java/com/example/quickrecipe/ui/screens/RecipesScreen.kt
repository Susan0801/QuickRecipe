package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickrecipe.R
import com.example.quickrecipe.model.MockRecipe
import com.example.quickrecipe.model.Recipe

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Recipe) -> Unit = {}
) {
    // State for selected cuisine filter
    var selectedCuisine by remember { mutableStateOf("All") }
    
    // Get recipes based on selected cuisine - use safe call to handle errors
    val recipes = try {
        MockRecipe.getRecipesByCuisine(selectedCuisine)
    } catch (e: Exception) {
        emptyList()
    }
    
    // Update cuisines to match what's in the mock data
    val cuisines = listOf("All", "Italian", "Mexican", "Chinese", "Indian", "Thai")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Discover Recipes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Cuisine filter chips
        CuisineFilterChips(
            cuisines = cuisines,
            selectedCuisine = selectedCuisine,
            onCuisineSelected = { selectedCuisine = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Recipe list
        RecipeList(
            recipes = recipes,
            onRecipeClick = onRecipeClick
        )
    }
}

@Composable
fun CuisineFilterChips(
    cuisines: List<String>,
    selectedCuisine: String,
    onCuisineSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cuisines) { cuisine ->
            val isSelected = cuisine == selectedCuisine
            
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (isSelected) Color(0xFF3DB489) else Color(0xFFF5F5F5)
                    )
                    .clickable { onCuisineSelected(cuisine) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cuisine,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit
) {
    if (recipes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No recipes found")
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe) }
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            // Recipe Image - Using AsyncImage which handles errors better
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
            
            // Recipe info overlay at the bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color(0x99000000))
                    .padding(16.dp)
            ) {
                Text(
                    text = recipe.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${recipe.cookingTime + recipe.prepTime} mins • ${recipe.cuisineType}",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = recipe.ingredients.take(3).joinToString(", ") + 
                           if (recipe.ingredients.size > 3) "..." else "",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        RecipesScreen()
    }
}
