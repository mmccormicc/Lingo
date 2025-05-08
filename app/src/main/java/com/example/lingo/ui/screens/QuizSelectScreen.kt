package com.example.lingo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.lingo.domain.QuizViewModel
import com.example.lingo.domain.QuizViewModelFactory
import com.example.lingo.navigation.Routes
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import com.example.lingo.utils.DeviceIdManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSelectScreen(navController: NavHostController, languageName: String) {

    val repository = remember { QuizRepository(RetrofitProvider.quizApiService) }
    val quizViewModel: QuizViewModel = viewModel(factory = QuizViewModelFactory(repository))

    // Observe scoresMap as State
    val scoresMap by quizViewModel.scoresMap.collectAsState()

    // Getting banner image depending on passed language name
    val imageResource = when (languageName.lowercase()) {
        "{spanish}" -> com.example.lingo.R.drawable.mexico_banner
        "{french}" -> com.example.lingo.R.drawable.france_banner
        "{german}" -> com.example.lingo.R.drawable.germany_banner
        "{italian}" -> com.example.lingo.R.drawable.italy_banner
        else -> com.example.lingo.R.drawable.germany_banner // Image to show if the name doesn't match
    }

    // List of dropdown options
    val options = listOf("Noun Quiz", "Verb Quiz")
    // If dropdown menu is expanded or not
    var expanded by remember { mutableStateOf(false) }
    // Option selected in dropdown menu
    var selectedOptionText by remember { mutableStateOf(options[0]) }


    LaunchedEffect(Unit) {
        // If device id is found
        DeviceIdManager.getCachedDeviceId()?.let { deviceId ->
            // For each quiz name
            options.forEach { quizName ->
                // Retrieve score from server
                quizViewModel.getScore(deviceId, languageName, quizName)
            }
        }
    }

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


        // Choose quiz text
        Text(
            text = "Choose\nQuiz",
            style = MaterialTheme.typography.displayMedium,
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
                // For each drop down menu item
                options.forEachIndexed { index, quizName ->
                    // Getting best score associated with quiz name
                    val bestScore = scoresMap[quizName]
                    // Getting number of questions in quiz
                    val numQuestions = quizViewModel.getNumQuestions(index, languageName)

                    // Each drop down menu item
                    DropdownMenuItem(
                        text = {
                            // Row that will put max space between name and score
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Quiz name text
                                Text(text = quizName, style = TextStyle(fontSize = 24.sp))
                                // Best score on quiz
                                bestScore?.let {
                                    Text(text = "Highest: $it/$numQuestions", style = TextStyle(fontSize = 20.sp, color = Color.Blue))
                                } ?: Text(text = "Not Taken", style = TextStyle(fontSize = 20.sp, color = Color.Blue))
                            }
                        },
                        onClick = {
                            // Updating text on drop down menu
                            selectedOptionText = quizName
                            // Minimizing menu
                            expanded = false

                            // Navigating to quiz from selected option
                            navController.navigate(Routes.quizScreen + "/$languageName/$index/$quizName")
                        }
                    )
                }
            }
        }

        // Home button column to align to bottom
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
                modifier = Modifier.size(80.dp).padding(bottom = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
            }
        }
    }
}