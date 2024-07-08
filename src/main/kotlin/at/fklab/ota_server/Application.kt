package at.fklab.ota_server

import at.fklab.ota_server.infra.configureDatabases
import at.fklab.ota_server.infra.configureHTTP
import at.fklab.ota_server.infra.configureSecurity
import at.fklab.ota_server.infra.configureSerialization
import at.fklab.ota_server.routes.firmwareReleasesRoute
import at.fklab.ota_server.routes.login
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

    val userService = UserService()
    val releaseTrainService = ReleaseTrainService()
    val firmwareReleaseService = FirmwareReleaseService()
    val fileService = FileService("/files")

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    val apiVersion = "v0.0.1"
    val restRoute = "rest"

    configureDatabases(
        environment.config.property("dataBase.DBURL").getString(),
        environment.config.property("dataBase.DBUSER").getString(),
        environment.config.property("dataBase.DBPW").getString(),
        environment.config.property("dataBase.INITDB").getString().toBoolean(),
        environment.config.property("dataBase.POPULTEDB").getString().toBoolean(),
        environment.config.property("dataBase.UPDATESCHEMA").getString().toBoolean()
    )

    configureSecurity(secret, issuer, audience, myRealm)
    configureHTTP()
    configureSerialization()

    routing {
        route("/$restRoute/$apiVersion") {
            login(userService, secret, issuer, audience, myRealm)
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            userRoute(userService)
            releaseTrainsRoute(releaseTrainService)
            firmwareReleasesRoute(firmwareReleaseService, fileService)
        }
    }
}
