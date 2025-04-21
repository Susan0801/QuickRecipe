package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
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
import com.example.quickrecipe.model.Difficulty

data class TimeFilter(val minutes: Int, val label: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Recipe) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCuisine by remember { mutableStateOf<String?>(null) }
    var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
    var selectedTimeFilter by remember { mutableStateOf<TimeFilter?>(null) }
    var isFiltersVisible by remember { mutableStateOf(false) }
    
    val cuisines = listOf("Mediterranean", "Asian", "Italian", "Mexican", "Indian", "Breakfast")
    val difficulties = listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD)
    val timeFilters = listOf(
        TimeFilter(15, "Under 15 min"),
        TimeFilter(30, "Under 30 min"),
        TimeFilter(60, "Under 1 hour")
    )

    // Calculate active filters count
    val activeFiltersCount = listOf(
        selectedCuisine,
        selectedDifficulty,
        selectedTimeFilter
    ).count { it != null }
    
    // Filter recipes based on all criteria
    val recipes = try {
        MockRecipe.getAllRecipes().filter { recipe ->
            val matchesCuisine = selectedCuisine == null || recipe.cuisineType == selectedCuisine
            val matchesDifficulty = selectedDifficulty == null || recipe.difficulty == selectedDifficulty
            val matchesTime = selectedTimeFilter?.let { timeFilter ->
                (recipe.cookingTime + recipe.prepTime) <= timeFilter.minutes
            } ?: true
            val matchesSearch = searchQuery.isEmpty() || 
                recipe.title.contains(searchQuery, ignoreCase = true) ||
                recipe.ingredients.any { it.contains(searchQuery, ignoreCase = true) }
            
            matchesCuisine && matchesDifficulty && matchesTime && matchesSearch
        }
    } catch (e: Exception) {
        emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Discover Recipes",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar and Filter Icon Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search recipes...") },
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                trailingIcon = if (searchQuery.isNotEmpty()) {
                    {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, "Clear search")
                        }
                    }
                } else null,
                singleLine = true,
                shape = RoundedCornerShape(28.dp)
            )

            // Filter Icon with Badge
            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(
                    onClick = { isFiltersVisible = !isFiltersVisible },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(
                        Icons.Default.FilterAlt,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (activeFiltersCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = activeFiltersCount.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filters Section - Only visible when isFiltersVisible is true
        if (isFiltersVisible) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (activeFiltersCount > 0) {
                        TextButton(
                            onClick = {
                                selectedCuisine = null
                                selectedDifficulty = null
                                selectedTimeFilter = null
                            }
                        ) {
                            Text("Clear all")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Cuisine Filters
                Text(
                    text = "Cuisine",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cuisines) { cuisine ->
                        FilterChip(
                            selected = selectedCuisine == cuisine,
                            onClick = { 
                                selectedCuisine = if (selectedCuisine == cuisine) null else cuisine
                            },
                            label = { Text(cuisine) },
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }

                // Difficulty Filters
                Text(
                    text = "Difficulty",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(difficulties) { difficulty ->
                        FilterChip(
                            selected = selectedDifficulty == difficulty,
                            onClick = { 
                                selectedDifficulty = if (selectedDifficulty == difficulty) null else difficulty
                            },
                            label = { Text(difficulty.name.lowercase().capitalize()) },
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }

                // Time Filters
                Text(
                    text = "Time",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(timeFilters) { timeFilter ->
                        FilterChip(
                            selected = selectedTimeFilter == timeFilter,
                            onClick = { 
                                selectedTimeFilter = if (selectedTimeFilter == timeFilter) null else timeFilter
                            },
                            label = { Text(timeFilter.label) },
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Recipe List
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
