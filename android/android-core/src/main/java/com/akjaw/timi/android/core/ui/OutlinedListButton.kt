package com.akjaw.timi.android.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akjaw.timi.android.core.view.theme.TimiComposeTheme
import com.akjaw.timi.android.core.view.theme.stopwatchBorder
import com.akjaw.timi.android.core.view.theme.taskShape

@Composable
fun OutlinedListButton(
    text: String,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(50.dp),
        shape = taskShape,
        border = stopwatchBorder(MaterialTheme.colors)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clickable { onClick() }
        ) {
            Text(text = text)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun OutlinedListButtonPreview() {
    TimiComposeTheme {
        OutlinedListButton("Text")
    }
}
