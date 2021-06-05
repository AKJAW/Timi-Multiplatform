package com.akjaw.timicompose

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage

fun SemanticsNodeInteraction.hasMainColor(color: String) {
    val imageBitmap = this.captureToImage()
    val firstPixelColor = imageBitmap.asAndroidBitmap().getPixel(15, 15) // TODO
    val actualHexColor = Integer.toHexString(firstPixelColor)
    try {
        assert(actualHexColor == color)
    } catch (e: Throwable) {
        throw AssertionError("Expected color: $color but was $actualHexColor")
    }
}
