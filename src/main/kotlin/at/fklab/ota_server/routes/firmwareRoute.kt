package at.fklab.ota_server.routes

import at.fklab.ota_server.models.FirmwareVersion
import at.fklab.ota_server.plugins.DEVICE_AUTH
import at.fklab.ota_server.services.AccessDeviceService
import at.fklab.ota_server.services.FirmwareVersionService
import io.ktor.client.engine.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.firmwareRoute(accessDeviceService: AccessDeviceService, firmwareVersionService: FirmwareVersionService) {
    route("/firmware") {
        authenticate(DEVICE_AUTH) {
            get {
                call.respond(firmwareVersionService.getAll())
            }

            get("{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val firmwareVersion: FirmwareVersion = firmwareVersionService.getById(UUID.fromString(id))
                    ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(firmwareVersion)
            }

            get("/deviceLatest/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(firmwareVersionService.getAll(id))
            }

            post {
                val firmwareVersion: FirmwareVersion = call.receive<FirmwareVersion>()
                call.respond(firmwareVersionService.add(firmwareVersion))
            }

            put {
                val firmwareVersion: FirmwareVersion = call.receive<FirmwareVersion>()
                call.respond(firmwareVersionService.update(firmwareVersion))
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                call.respond(firmwareVersionService.delete(UUID.fromString(id)))
            }
        }
    }
}