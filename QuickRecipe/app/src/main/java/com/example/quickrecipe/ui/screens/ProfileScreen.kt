package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickrecipe.model.User
import com.example.quickrecipe.data.repository.UserRepository
import com.example.quickrecipe.ui.components.QuickRecipeBottomNavBar
import com.example.quickrecipe.ui.util.WindowSize
import com.example.quickrecipe.ui.util.rememberWindowSize
import com.example.quickrecipe.ui.util.getResponsivePadding
import com.example.quickrecipe.ui.util.getResponsiveFontSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    currentUser: User,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onLogout: () -> Unit,
    currentNavigationIndex: Int,
    onNavigate: (Int) -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val windowSize = rememberWindowSize()
    val padding = getResponsivePadding(windowSize)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            QuickRecipeBottomNavBar(
                currentIndex = currentNavigationIndex,
                onTabSelected = { index -> 
                    onNavigate(index)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .align(if (windowSize == WindowSize.COMPACT) Alignment.TopCenter else Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(padding)
            ) {
                // Profile Information
                Text(
                    text = currentUser.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = getResponsiveFontSize(windowSize, 24).sp
                    )
                )
                
                Text(
                    text = currentUser.email,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = getResponsiveFontSize(windowSize, 16).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (currentUser.bio != null) {
                    Text(
                        text = currentUser.bio,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = getResponsiveFontSize(windowSize, 14).sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = padding / 2)
                    )
                }

                // Stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = padding),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatColumn("Recipes", currentUser.recipesCreated ?: 0, windowSize)
                    StatColumn("Followers", currentUser.followersCount ?: 0, windowSize)
                    StatColumn("Following", currentUser.followingCount ?: 0, windowSize)
                }
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { 
                Text(
                    "Logout",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = getResponsiveFontSize(windowSize, 20).sp
                    )
                ) 
            },
            text = { 
                Text(
                    "Are you sure you want to logout?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = getResponsiveFontSize(windowSize, 16).sp
                    )
                ) 
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        UserRepository.logout(context)
                        onLogout()
                    }
                ) {
                    Text(
                        "Logout",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = getResponsiveFontSize(windowSize, 14).sp
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(
                        "Cancel",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = getResponsiveFontSize(windowSize, 14).sp
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun StatColumn(
    label: String,
    value: Int,
    windowSize: WindowSize,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = getResponsiveFontSize(windowSize, 20).sp
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = getResponsiveFontSize(windowSize, 14).sp
            )
        )
    }
} 