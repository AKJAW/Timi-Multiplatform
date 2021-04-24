package com.akjaw.task.list

import com.akjaw.task.TaskEntityQueries
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class InMemoryTaskEntityQueriesFactory {

    fun create(): TaskEntityQueries {
        val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            Database.Schema.create(this)
        }
        return Database(inMemorySqlDriver).taskEntityQueries
    }
}
