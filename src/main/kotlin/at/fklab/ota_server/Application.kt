package at.fklab.ota_server

import at.fklab.ota_server.infra.configureDatabases
import at.fklab.ota_server.infra.configureHTTP
import at.fklab.ota_server.infra.configureSerialization
import at.fklab.ota_server.routes.firmwareReleasesRoute
import at.fklab.ota_server.routes.releaseTrainsRoute
import at.fklab.ota_server.routes.userRoute
import at.fklab.ota_server.services.FileService
import at.fklab.ota_server.services.FirmwareReleaseService
import at.fklab.ota_server.services.ReleaseTrainService
import at.fklab.ota_server.services.UserService
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {

    val dbUrl: String = environment.config.property("dataBase.DBURL").getString()

    val dbUser: String = environment.config.property("dataBase.DBUSER").getString()
    val dbPW: String = environment.config.property("dataBase.DBPW").getString()

    val initDB: Boolean = environment.config.property("dataBase.INITDB").getString().toBoolean()
    val populateDB: Boolean = environment.config.property("dataBase.POPULTEDB").getString().toBoolean()
    val updateSchema: Boolean = environment.config.property("dataBase.UPDATESCHEMA").getString().toBoolean()

    val userService = UserService()
    val releaseTrainService = ReleaseTrainService()
    val firmwareReleaseService = FirmwareReleaseService()
    val fileService = FileService("/files")

    configureDatabases(dbUrl, dbUser, dbPW, updateSchema, initDB, populateDB)

    configureHTTP()
    configureSerialization()

    val apiVersion = "v0.0.1"
    val restRoute = "rest"


    routing {
        route("/$restRoute/$apiVersion") {
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            userRoute(userService)
            releaseTrainsRoute(releaseTrainService)
            firmwareReleasesRoute(firmwareReleaseService, fileService)
        }
    }
}
