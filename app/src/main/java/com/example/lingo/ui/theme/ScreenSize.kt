package com.example.lingo.ui.theme


import androidx.compose.runtime.staticCompositionLocalOf

data class ScreenSize(val widthDp: Int, val heightDp: Int)

val LocalScreenSize = staticCompositionLocalOf<ScreenSize> {
    error("Screen size not provided")
}