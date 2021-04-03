package com.example.timicompose.common.domain

import com.example.timicompose.common.domain.model.TimestampMilliseconds

interface TimestampProvider {

    fun getMilliseconds(): TimestampMilliseconds
}
