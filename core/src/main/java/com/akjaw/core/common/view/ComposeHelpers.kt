package com.akjaw.core.common.view

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun pluralStringResource(
    @PluralsRes id: Int,
    quantity: Int,
    vararg formatArgs: Any? = emptyArray()
): String = LocalContext.current.resources.getQuantityString(id, quantity, *formatArgs)
