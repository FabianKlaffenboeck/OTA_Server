package at.fklab.ota_server.routes

import at.fklab.ota_server.plugins.DEVICE_AUTH
import at.fklab.ota_server.services.AccessDeviceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.firmwareRoute(accessDeviceService: AccessDeviceService) {
    route("/firmware") {
        authenticate(DEVICE_AUTH) {
            get {
                val device_id = call.principal<UserIdPrincipal>()?.name
                call.respond(accessDeviceService.getAll())
            }
        }
    }
}