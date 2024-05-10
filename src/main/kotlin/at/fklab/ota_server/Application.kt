package at.fklab.ota_server

import at.fklab.ota_server.plugins.configureDatabases
import at.fklab.ota_server.plugins.configureHTTP
import at.fklab.ota_server.plugins.configureSecurity
import at.fklab.ota_server.plugins.configureSerialization
import at.fklab.ota_server.routes.accessDeviceRoute
import at.fklab.ota_server.routes.firmwareRoute
import at.fklab.ota_server.services.AccessDeviceService
import at.fklab.ota_server.services.AccessTokenService
import at.fklab.ota_server.services.FirmwareVersionService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val dbUrl: String = System.getenv("DBURL") ?: "jdbc:sqlite:TestDB"

    val dbUser: String = System.getenv("DBUSER") ?: "root"
    val dbPW: String = System.getenv("DBPW") ?: ""

    val initDB: Boolean = System.getenv("INITDB").toBoolean()
    val populateDB: Boolean = System.getenv("POPULTEDB").toBoolean()
    val updateSchema: Boolean = System.getenv("UPDATESCHEMA").toBoolean()

    val accessDeviceService = AccessDeviceService()
    val firmwareVersionService = FirmwareVersionService()
    val accessTokenService = AccessTokenService()

    configureDatabases(dbUrl, dbUser, dbPW, updateSchema, initDB, populateDB)

    configureHTTP()
    configureSerialization()
    configureSecurity()

    val apiVersion = "v0.0.1"

    routing {
        route("/api/$apiVersion") {
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            firmwareRoute(accessDeviceService, firmwareVersionService)
            accessDeviceRoute(accessDeviceService,accessTokenService)
        }
    }
}
