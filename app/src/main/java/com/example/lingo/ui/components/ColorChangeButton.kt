package com.example.lingo.ui.components

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingo.domain.BaseQuestionsViewModel
import com.example.lingo.domain.PictureMatchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ColorChangeButton(viewModel: BaseQuestionsViewModel, isCorrect : Boolean, buttonText : String, key: Int) {

    // Colors used by button
    val defaultColor = MaterialTheme.colorScheme.primary
    val correctColor = Color.Green
    val incorrectColor = Color.Red

    // Current button color
    var buttonColor by remember(key) { mutableStateOf(defaultColor) }

    // Scope to start coroutine
    val scope = rememberCoroutineScope()


    // Animating color change
    val animatedColor by animateColorAsState(
        targetValue = buttonColor,
        // Color change duration
        animationSpec = tween(500),
        label = "Color Animation"
    )

    // Clickable button background
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                // Launching coroutine to wait until animation is finished before going to next question
                scope.launch {
                    // Setting button color based on if it was correct answer or not
                    if (isCorrect) {
                        buttonColor = correctColor
                    } else {
                        buttonColor = incorrectColor
                    }
                    // Waiting for animation
                    delay(600)
                    // Going to next question
                    viewModel.nextQuestion()
                }

            }
            .fillMaxWidth(),

        color = animatedColor, // Apply the animated color to the Surface
        shape = MaterialTheme.shapes.small // Use a shape from the Material 3 theme
    ) {
        // Button text
        Text(
            text = buttonText,
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}

