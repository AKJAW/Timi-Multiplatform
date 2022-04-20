package com.akjaw.timi.android.app

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage

// TODO refactor to use Compose Color
fun SemanticsNodeInteraction.hasMainColor(color: String) {
    val imageBitmap = this.captureToImage()
    val firstPixelColor = imageBitmap.asAndroidBitmap().getPixel(15, 15) // TODO
    val actualHexColor = Integer.toHexString(firstPixelColor)
    assert(actualHexColor == color) {
        "Expected color: $color but was $actualHexColor"
    }
}
