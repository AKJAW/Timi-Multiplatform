package com.example.timicompose.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timicompose.tasks.presentation.TaskViewModel
import com.example.timicompose.tasks.presentation.TasksViewModelImpl

class MyViewModelProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == TaskViewModel::class.java) {
            TasksViewModelImpl() as T
        } else {
            throw IllegalArgumentException("View model $modelClass not supported")
        }
    }
}
