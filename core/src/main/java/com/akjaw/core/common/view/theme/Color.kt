package com.akjaw.core.common.view.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val taskColors = listOf(
    Color(239, 132, 132),
    Color(240, 152, 132),
    Color(239, 187, 132),
    Color(230, 240, 132),
    Color(190, 239, 132),
    Color(132, 240, 161),
    Color(132, 239, 212),
    Color(132, 212, 240),
    Color(132, 162, 239),
    Color(164, 132, 239),
    Color(206, 132, 239),
    Color(239, 132, 214),
)

private val whiteTextColor = Color(230, 230, 230)
private val blackTextColor = Color(10, 10, 10)

fun taskTextColorFor(color: Color): Color {
    return if (color.luminance() < 0.5) {
        whiteTextColor
    } else {
        blackTextColor
    }
}
