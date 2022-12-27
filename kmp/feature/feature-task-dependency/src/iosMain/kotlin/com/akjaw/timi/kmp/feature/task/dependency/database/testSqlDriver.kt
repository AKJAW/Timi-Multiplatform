package com.akjaw.timi.kmp.feature.database

import co.touchlab.sqliter.DatabaseConfiguration
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection

private var index = 0

internal actual fun createTestSqlDriver(): SqlDriver {
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
