package com.akjaw.timi.kmp.feature.database.composition

import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module

actual fun Module.databasePlatformModule() {
    single<SqlDriver> { NativeSqliteDriver(TimiDatabase.Schema, "timi-task.db") }
}
