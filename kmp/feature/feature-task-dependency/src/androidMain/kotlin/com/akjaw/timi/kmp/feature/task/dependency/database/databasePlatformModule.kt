package com.akjaw.timi.kmp.feature.task.dependency.database

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module

actual fun Module.databasePlatformModule() {
    single<SqlDriver> { AndroidSqliteDriver(TimiDatabase.Schema, get(), "timi-task.db") }
}
