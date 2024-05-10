package at.fklab.ota_server.routes

import at.fklab.ota_server.models.AccessDevice
import at.fklab.ota_server.models.FirmwareVersion
import at.fklab.ota_server.plugins.DEVICE_AUTH
import at.fklab.ota_server.services.AccessDeviceService
import at.fklab.ota_server.services.AccessTokenService
import at.fklab.ota_server.services.FirmwareVersionService
import io.ktor.client.engine.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.accessDeviceRoute(accessDeviceService: AccessDeviceService, accessTokenService: AccessTokenService) {
    route("/accessDevices") {
        authenticate(DEVICE_AUTH) {
            get {
                call.respond(accessDeviceService.getAll())
            }

            get("{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val accessDevice: AccessDevice =
                    accessDeviceService.getById(UUID.fromString(id)) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(accessDevice)
            }

            post {
                val accessDevice: AccessDevice = call.receive<AccessDevice>()
                call.respond(accessDeviceService.add(accessDevice))
            }

            put {
                val accessDevice: AccessDevice = call.receive<AccessDevice>()
                call.respond(accessDeviceService.update(accessDevice))
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                call.respond(accessDeviceService.delete(UUID.fromString(id)))
            }

            get("/{id}/tokens") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(accessTokenService.getAll(id))
            }

            get("/{id}/newToken") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(accessTokenService.add(UUID.fromString(id)))
            }

        }
    }
}