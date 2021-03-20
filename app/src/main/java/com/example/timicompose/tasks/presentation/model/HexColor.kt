package com.example.timicompose.tasks.presentation.model

import androidx.compose.ui.graphics.Color

data class HexColor(
    val value: String,
)

fun HexColor.toColor(): Color {
    val parsedColor = android.graphics.Color.parseColor(this.value)
    return Color(parsedColor)
}
