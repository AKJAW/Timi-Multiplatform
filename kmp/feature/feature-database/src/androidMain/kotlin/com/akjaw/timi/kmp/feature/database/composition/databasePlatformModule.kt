package com.akjaw.timi.kmp.feature.database.composition

import androidx.sqlite.db.SupportSQLiteDatabase
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module

actual fun Module.databasePlatformModule() {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = TimiDatabase.Schema,
            context = get(),
            name = "timi-task.db",
            callback = object : AndroidSqliteDriver.Callback(TimiDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.execSQL("PRAGMA foreign_keys=ON;")
                }
            }
        )
    }
}
