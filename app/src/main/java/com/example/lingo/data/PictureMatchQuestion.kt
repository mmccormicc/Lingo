package com.example.lingo.data

data class PictureMatchQuestion(
    // Holds location of picture in drawable
    val pictureID: Int,
    // Index of correct answer
    val answer: Int,
    // List of options to choose
    val options: List<String>
)