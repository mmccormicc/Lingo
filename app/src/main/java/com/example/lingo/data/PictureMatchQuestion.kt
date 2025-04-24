package com.example.lingo.data

data class PictureMatchQuestion(
    val pictureID: Int,
    val answer: Int,
    val options: List<String>
)