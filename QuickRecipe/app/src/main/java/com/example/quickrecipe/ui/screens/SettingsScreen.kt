package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quickrecipe.ui.theme.rememberThemeState

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val (isDarkMode, onThemeChanged) = rememberThemeState()

    var notificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Preferences",
                fontSize = 16.sp,
                color = colors.outline,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Language
            SettingsItem(
                iconText = "🌐",
                title = "Language",
                endContent = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedLanguage, color = colors.outline)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Select language",
                            tint = colors.outline
                        )
                    }
                },
                onClick = { showLanguageDialog = true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dark Mode
            SettingsItem(
                iconText = "🌙",
                title = "Dark Mode",
                endContent = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onThemeChanged(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.onPrimary,
                            checkedTrackColor = colors.primary,
                            uncheckedThumbColor = colors.onSurface,
                            uncheckedTrackColor = colors.surfaceVariant
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Notifications
            SettingsItem(
                iconText = "🔔",
                title = "Notifications",
                endContent = {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.onPrimary,
                            checkedTrackColor = colors.primary,
                            uncheckedThumbColor = colors.onSurface,
                            uncheckedTrackColor = colors.surfaceVariant
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "About",
                fontSize = 16.sp,
                color = colors.outline,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SettingsItem(
                iconText = "🍕",
                title = "Share App",
                onClick = {
                    // TODO: Share intent logic
                }
            )
        }
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            selectedLanguage = selectedLanguage,
            onLanguageSelected = {
                selectedLanguage = it
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
fun SettingsItem(
    iconText: String,
    title: String,
    endContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(colors.surfaceVariant)
            .clickable(onClick = onClick ?: {})
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = iconText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                color = colors.onSurface,
                modifier = Modifier.weight(1f)
            )

            endContent?.invoke()
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf("English", "Spanish", "French", "German", "Chinese", "Japanese", "Korean")
    val colors = MaterialTheme.colorScheme

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = colors.surfaceVariant
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Select Language",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                languages.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(language) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = language == selectedLanguage,
                            onClick = { onLanguageSelected(language) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colors.primary
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = language, color = colors.onSurface)
                    }
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Cancel",
                        color = colors.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        SettingsScreen()
    }
}
