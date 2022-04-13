package com.akjaw.core.common.view.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

private val whiteTextColor = Color(230, 230, 230)
private val blackTextColor = Color(10, 10, 10)

fun taskTextColorFor(color: Color): Color {
    return if (color.luminance() < 0.5) {
        whiteTextColor
    } else {
        blackTextColor
    }
}
