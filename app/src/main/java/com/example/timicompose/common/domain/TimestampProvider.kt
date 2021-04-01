package com.example.timicompose.common.domain

import com.example.timicompose.stopwatch.domain.model.TimestampMilliseconds

interface TimestampProvider {

    fun getMilliseconds(): TimestampMilliseconds
}
