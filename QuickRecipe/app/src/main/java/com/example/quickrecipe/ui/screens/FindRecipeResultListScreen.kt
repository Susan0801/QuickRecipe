package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickrecipe.model.MockRecipe
import com.example.quickrecipe.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindRecipeResultListScreen(
    ingredients: List<String>,
    onBackPressed: () -> Unit = {},
    onRecipeClick: (Recipe) -> Unit = {}
) {
    val matchingRecipes = remember(ingredients) {
        // Filter recipes that contain all the ingredients
        MockRecipe.getAllRecipes().filter { recipe ->
            ingredients.all { ingredient ->
                recipe.ingredients.any { it.contains(ingredient, ignoreCase = true) }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
            TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Recipe Matches",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${matchingRecipes.size} recipes found",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (matchingRecipes.isEmpty()) {
            // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                        text = "No Matching Recipes",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                        text = "Try different ingredients or remove some to find more recipes",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
            // Recipe list
                LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Ingredients used section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Ingredients Used",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ingredients.forEach { ingredient ->
                                Text(
                                    text = "• $ingredient",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                // Recipe cards
                items(matchingRecipes) { recipe ->
                    RecipeMatchCard(
                        recipe = recipe,
                        ingredients = ingredients,
                        onClick = { onRecipeClick(recipe) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeMatchCard(
    recipe: Recipe,
    ingredients: List<String>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        onClick = onClick
    ) {
        Row(
                modifier = Modifier
                    .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                    Text(
                    text = "Cooking time: ${recipe.cookingTime + recipe.prepTime} mins",
                    style = MaterialTheme.typography.bodyMedium
                    )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${recipe.ingredients.size} ingredients (${ingredients.size} available)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
