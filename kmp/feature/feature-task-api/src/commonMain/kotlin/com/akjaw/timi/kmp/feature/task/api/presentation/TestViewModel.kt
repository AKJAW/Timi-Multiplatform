package com.akjaw.timi.kmp.feature.task.api.presentation

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn as normalStateIn

class TestViewModel : KMMViewModel() {

    val intViewModelScopeFlow: StateFlow<Int> = createFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val intCoroutinesCopeFlow: StateFlow<Int> = createFlow()
        .normalStateIn(viewModelScope.coroutineScope, SharingStarted.WhileSubscribed(), 0)

    private fun createFlow() = flow {
        var i = 1
        while (true) {
            delay(1000)
            emit(i++)
        }
    }
}