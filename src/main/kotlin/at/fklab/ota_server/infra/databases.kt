package at.fklab.ota_server.infra

import at.fklab.ota_server.development.sampleFirmwareReleases
import at.fklab.ota_server.development.sampleReleaseTrains
import at.fklab.ota_server.development.sampleUsers
import at.fklab.ota_server.models.FirmwareReleases
import at.fklab.ota_server.models.ReleaseTrains
import at.fklab.ota_server.models.Tokens
import at.fklab.ota_server.models.Users
import at.fklab.ota_server.services.FirmwareReleaseService
import at.fklab.ota_server.services.ReleaseTrainService
import at.fklab.ota_server.services.UserService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val tables = listOf(Users, FirmwareReleases, ReleaseTrains, Tokens)


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
    for (user in sampleUsers) {
        UserService().add(user)
    }
    for (releaseTrain in sampleReleaseTrains) {
        ReleaseTrainService().add(releaseTrain)
    }
    for (firmwareRelease in sampleFirmwareReleases) {
        FirmwareReleaseService().add(firmwareRelease)
    }
}
