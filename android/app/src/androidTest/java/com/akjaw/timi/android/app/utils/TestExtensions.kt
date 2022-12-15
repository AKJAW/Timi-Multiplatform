package com.akjaw.timi.android.app.utils

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import com.akjaw.timi.android.app.ActivityComposeTestRule

fun SemanticsNodeInteractionCollection.assertDescendantDoesNotContainText(
    text: String
): SemanticsNodeInteractionCollection {
    val nodes = this.filter(hasAnyDescendant(hasText(text)))

    assert(nodes.fetchSemanticsNodes(atLeastOneRootRequired = false).isEmpty()) {
        "Descendant with the text: $text exists but should not"
    }

    return this
}

fun ActivityComposeTestRule.getString(@StringRes id: Int): String =
    this.activity.getString(id)
