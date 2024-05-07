package at.fklab.ota_server.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.firmwareRoute() {
    route("/firmware") {
        get {
            val devId = call.request.headers["device-id"]
            call.request.headers["device-auth-token"]
        }
    }
}