package com.akjaw.timi.kmp.feature.task.dependency.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module

actual fun Module.databasePlatformModule() {
    single<SqlDriver> { NativeSqliteDriver(TimiDatabase.Schema, "timi-task.db") }
}
