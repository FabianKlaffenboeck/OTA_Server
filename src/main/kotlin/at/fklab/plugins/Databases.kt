package at.fklab.plugins

import at.fklab.model.AccessDevices
import at.fklab.model.AccessTokens
import at.fklab.model.FirmwareVersions
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val tables = listOf(AccessDevices, AccessTokens, FirmwareVersions)


fun Application.configureDatabases(
    dbUrl: String, dbUser: String, dbPW: String, updateSchema: Boolean, initDB: Boolean, populateDB: Boolean
) {

    val database = Database.connect(url = dbUrl, user = dbUser, password = dbPW)

    if (initDB) {
        initDB()
    }

    if (updateSchema) {
        updateTables()
    }

    if (populateDB) {
        populateDB()
    }
}

fun initDB() {
    transaction {
        tables.forEach { table ->
            SchemaUtils.drop(table)
        }
    }
    transaction {
        tables.forEach { table ->
            SchemaUtils.create(table)
        }
    }
}

fun updateTables() {
    transaction {
        tables.forEach { table ->
            SchemaUtils.createMissingTablesAndColumns(table)
        }
    }
}

fun populateDB() {

}
