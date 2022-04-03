package com.akjaw.details.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.akjaw.core.common.view.NavigationHolder

internal class TaskDetailScreenCreatorImpl() : TaskDetailScreenCreator {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Create(navigationHolder: NavigationHolder) {
        TaskDetailScreen(navigationHolder.controller)
    }
}