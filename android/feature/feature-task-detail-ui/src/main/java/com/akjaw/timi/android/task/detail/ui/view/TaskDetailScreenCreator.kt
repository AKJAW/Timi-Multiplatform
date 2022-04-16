package com.akjaw.timi.android.task.detail.ui.view

import androidx.compose.runtime.Composable
import com.akjaw.timi.android.core.view.NavigationHolder

interface TaskDetailScreenCreator {

    @Composable
    fun Create(navigationHolder: NavigationHolder)
}
