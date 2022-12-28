package com.akjaw.timi.kmp.feature.database.test

import co.touchlab.sqliter.DatabaseConfiguration
import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection

private var index = 0

actual fun createTestSqlDriver(): SqlDriver {
    index++
    val schema = TimiDatabase.Schema
    return NativeSqliteDriver(
        DatabaseConfiguration(
            name = "test-$index.db",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            },
            inMemory = true
        )
    )
}
