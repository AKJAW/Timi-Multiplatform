package com.akjaw.task.list

import com.akjaw.timi.kmp.feature.task.dependency.database.TaskEntityQueries
import com.akjaw.timi.kmp.feature.task.dependency.database.TimiDatabase
import com.akjaw.timi.kmp.feature.task.dependency.list.data.taskColorAdapter
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class InMemoryTaskEntityQueriesFactory {

    fun create(): TaskEntityQueries {
        val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            TimiDatabase.Schema.create(this)
        }
        return TimiDatabase(
            inMemorySqlDriver,
            taskColorAdapter
        ).taskEntityQueries
    }
}
