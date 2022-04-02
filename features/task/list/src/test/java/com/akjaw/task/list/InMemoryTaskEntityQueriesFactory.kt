package com.akjaw.task.list

import com.akjaw.task.TaskEntity
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.data.taskColorAdapter
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class InMemoryTaskEntityQueriesFactory {

    fun create(): TaskEntityQueries {
        val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            Database.Schema.create(this)
        }
        return Database(inMemorySqlDriver, taskColorAdapter).taskEntityQueries
    }
}
