package com.akjaw.timi.kmp.feature.database.test

import com.akjaw.timi.kmp.feature.database.TimiDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

// TODO refactor to use robolectric?
actual fun createTestSqlDriver(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    TimiDatabase.Schema.create(driver)
    return driver.also {
        driver.execute(null, "PRAGMA foreign_keys=ON", 0)
    }
}
