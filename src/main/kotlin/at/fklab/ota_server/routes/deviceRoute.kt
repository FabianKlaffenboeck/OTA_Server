package at.fklab.ota_server.routes

import at.fklab.ota_server.models.Device
import at.fklab.ota_server.models.User
import at.fklab.ota_server.services.DeviceService
import at.fklab.ota_server.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deviceRoute(deviceService: DeviceService) {
    route("/devices") {
        get {
            call.respond(deviceService.getAll())
        }

        get("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val device = deviceService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(device)
        }

        post {
            val device = call.receive<Device>()
            call.respond(deviceService.add(device))
        }

        put {
            val device = call.receive<Device>()
            call.respond(deviceService.update(device))
        }

        delete("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(deviceService.delete(id))
        }
    }
}