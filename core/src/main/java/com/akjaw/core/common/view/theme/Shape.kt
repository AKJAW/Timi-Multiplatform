package com.akjaw.core.common.view.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val taskShape = RoundedCornerShape(8.dp)

fun stopwatchBorder(colors: Colors): BorderStroke =
    BorderStroke(1.dp, colors.onBackground.copy(alpha = 0.1f))
