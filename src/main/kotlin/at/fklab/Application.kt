package at.fklab

import at.fklab.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
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

    configureDatabases(dbUrl, dbUser, dbPW, updateSchema, initDB, populateDB)
    configureHTTP()
    configureSerialization()

    routing {
        route("/api") {
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
            get() {
                call.respondText("Hello World!")
            }
        }
    }
}
