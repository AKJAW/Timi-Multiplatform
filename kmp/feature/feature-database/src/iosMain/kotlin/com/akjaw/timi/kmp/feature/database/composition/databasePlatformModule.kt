package com.akjaw.timi.kmp.feature.database.composition

import co.touchlab.sqliter.DatabaseConfiguration
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import org.koin.core.module.Module

actual fun Module.databasePlatformModule() {
    single<SqlDriver>(createdAtStart = true) {
        val schema = TimiDatabase.Schema
        val config = DatabaseConfiguration(
            name = "timi-task.db",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) {
                    schema.create(it)
                }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            }
        )
        NativeSqliteDriver(config).apply {
            execute(null, "PRAGMA foreign_keys=ON;", 0)
        }
    }
}
