package com.akjaw.timicompose.composition

import android.content.Context
import com.akjaw.task.TaskEntityQueries
import com.akjaw.task.list.Database
import com.akjaw.task.list.composition.TaskListDatabaseModule
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TaskListDatabaseModule::class],
)
object TestTaskListDatabaseModule {

    @Provides
    @Singleton
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "test-task.db")
    }

    @Provides
    @Singleton
    fun provideDatabase(
        sqlDriver: SqlDriver
    ): Database {
        return Database(
            driver = sqlDriver
        )
    }

    @Provides
    fun provideTaskEntityQueries(
        database: Database
    ): TaskEntityQueries {
        return database.taskEntityQueries
    }
}
