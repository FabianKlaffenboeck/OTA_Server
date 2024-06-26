package at.fklab.ota_server.plugins

import at.fklab.ota_server.development.*
import at.fklab.ota_server.models.*
import at.fklab.ota_server.services.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val tables = listOf(Users, Devices, FirmwareReleases, ReleaseTrains)


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
    for (token in sampleAccessTokens) {
        TokenService().add(token)
    }
    for (user in sampleUsers) {
        UserService().add(user)
    }
    for (device in sampleDevices) {
        DeviceService().add(device)
    }
    for (releaseTrain in sampleReleaseTrains) {
        ReleaseTrainService().add(releaseTrain)
    }
    for (firmwareRelease in sampleFirmwareReleases) {
        FirmwareReleaseService().add(firmwareRelease)
    }
}
