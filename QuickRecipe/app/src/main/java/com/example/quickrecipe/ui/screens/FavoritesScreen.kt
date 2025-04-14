package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.quickrecipe.data.repository.FavoritesRepository
import com.example.quickrecipe.model.MockRecipe
import com.example.quickrecipe.model.Recipe
import java.text.SimpleDateFormat
import java.util.*

data class SavedRecipe(
    val recipe: Recipe,
    val savedDate: Date = Date()
)

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Recipe) -> Unit = {},
    onDiscoverRecipesClick: () -> Unit = {}
) {
    // Get favorites from repository
    val favoritesState = remember { mutableStateOf(FavoritesRepository.getAllFavorites()) }
    
    // Update favorites when they change
    LaunchedEffect(FavoritesRepository.getFavoritesCount()) {
        favoritesState.value = FavoritesRepository.getAllFavorites()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Saved Recipes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        if (favoritesState.value.isEmpty()) {
            // Empty state
            EmptyFavoritesState(
                modifier = Modifier.weight(1f),
                onDiscoverRecipesClick = onDiscoverRecipesClick
            )
        } else {
            // List of saved recipes
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(favoritesState.value) { savedRecipe ->
                    SavedRecipeItem(
                        savedRecipe = savedRecipe,
                        onRecipeClick = { onRecipeClick(savedRecipe.recipe) },
                        onRemoveClick = {
                            // Remove recipe from favorites
                            FavoritesRepository.removeFromFavorites(savedRecipe.recipe.id)
                            // Update the UI
                            favoritesState.value = FavoritesRepository.getAllFavorites()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedRecipeItem(
    savedRecipe: SavedRecipe,
    onRecipeClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formattedDate = dateFormat.format(savedRecipe.savedDate)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onRecipeClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Recipe Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                savedRecipe.recipe.imageUrl?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = savedRecipe.recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
            }
            
            // Recipe Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = savedRecipe.recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Saved on $formattedDate",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            
            // Favorite Button
            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun EmptyFavoritesState(
    modifier: Modifier = Modifier,
    onDiscoverRecipesClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Heart icon with circle background
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFEEEEEE)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorites",
                tint = Color.Gray,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No saved recipes yet",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Your favorite recipes will appear here for quick access",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onDiscoverRecipesClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3DB489))
        ) {
            Text(
                text = "Discover Recipes",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FavoritesScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyFavoritesPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Saved Recipes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            EmptyFavoritesState(
                modifier = Modifier.weight(1f)
            )
        }
    }
}
