package com.escodro.local.provider

import app.cash.sqldelight.db.SqlDriver

/**
 * Provides the platform-specific [SqlDriver] to be used in the database.
 */
internal interface DriverFactory {

    /**
     * Creates the platform-specific [SqlDriver] to be used in the database.
     *
     * @param databaseName the database name
     *
     * @return the [SqlDriver] to be used in the database
     */
    fun createDriver(databaseName: String): SqlDriver
}
