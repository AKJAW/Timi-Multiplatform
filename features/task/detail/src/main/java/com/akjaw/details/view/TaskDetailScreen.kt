package com.akjaw.details.view

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akjaw.core.common.presentation.TimiBottomBar

@ExperimentalMaterialApi
@Composable
fun TaskDetailScreen(navController: NavHostController) {
    val state = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    Scaffold(
        bottomBar = { TimiBottomBar(navController) },
    ) {
        BottomSheetScaffold(
            scaffoldState = state,
            sheetContent = {
                Text(
                    modifier = Modifier
                        .height(500.dp),
                    text = "BottomSheet"
                )
            },
            sheetPeekHeight = 80.dp
        ) {
            Text("TaskDetailScreen")
        }
    }
}
