package com.example.lingo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.lingo.R
import com.example.lingo.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardSelectScreen(navController: NavHostController, languageName: String) {
    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> R.drawable.mexico_banner
        "{french}" -> R.drawable.france_banner
        "{german}" -> R.drawable.germany_banner
        "{italian}" -> R.drawable.italy_banner
        else -> R.drawable.germany_banner // Image to show if the name doesn't match
    }

    val options = listOf("Option A", "Option B", "Option C", "Option D")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Column(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Painting banner
        AsyncImage(
            model = imageResource,
            contentDescription = languageName + "Banner",
            modifier = Modifier.fillMaxWidth()
        )



        // Choose flashcard category text
        Text(
            text = "Choose\nFlashcard\nCategory",
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(64.dp)
        )

        // Dropdown menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.width(300.dp)
        ) {
            TextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(),
                textStyle = TextStyle(fontSize = 24.sp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {Text(text = selectionOption, style = TextStyle(
                            fontSize = 24.sp))},
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        // Home button column
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Home button
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.onSurfaceVariant),
                onClick = {
                    navController.navigate(Routes.homeScreen + "/$languageName")
                },
                modifier = Modifier.size(150.dp).padding(bottom = 100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(150.dp),
                    tint = Color.White
                )
            }
        }
    }
}