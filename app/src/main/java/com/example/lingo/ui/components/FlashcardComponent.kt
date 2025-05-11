package com.example.lingo.ui.components

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingo.domain.BaseQuestionsViewModel
import com.example.lingo.domain.FlashcardViewModel
import com.example.lingo.domain.PictureMatchViewModel
import com.example.lingo.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FlashcardComponent(viewModel: FlashcardViewModel) {
    // Getting current card to be displayed
    var card = viewModel.currentCard

    // Setting flashcard colors
    val englishColor = MaterialTheme.colorScheme.onSurfaceVariant
    val translatedColor = MaterialTheme.colorScheme.primary

    // Current displayed text
    var flashcardText by remember {mutableStateOf(card.englishSide)}
    // Current color
    var flashcardColor by remember { mutableStateOf(englishColor) }
    // On english side
    var showingEnglish by remember { mutableStateOf(true) };

    // Clickable button background
    Surface(
        modifier = Modifier
            .padding(24.dp)
            .clickable {
                // Update to translated side
                if (showingEnglish) {
                    flashcardText = card.translatedSide
                    flashcardColor = translatedColor
                    showingEnglish = false
                // Update to english side
                } else {
                    flashcardText = card.englishSide
                    flashcardColor = englishColor
                    showingEnglish = true
                }
            }
            // Flashcard should fill max width and keep a square aspect ratio
            .widthIn(max = 350.dp)
            .aspectRatio(1f),

        color = flashcardColor,
        shape = MaterialTheme.shapes.large
    ) {
        // This box centers the text in the flashcard
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Text inside flashcard
            Text(
                text = flashcardText,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(8.dp),
            )

            // Flip icon in top right corner
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Flip Card Over",
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    // Next flashcard button
    Button(
        modifier = Modifier.padding(8.dp),
        onClick = {
            // Going to next card
            viewModel.nextCard()
            // Pulling new card information
            card = viewModel.currentCard
            flashcardText = card.englishSide
            flashcardColor = englishColor
            showingEnglish = true
        }
    ) {
        // Next text
        Text(
            text = "Next Flashcard",
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        )
    }
}

