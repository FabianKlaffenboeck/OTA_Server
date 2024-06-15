package at.fklab.ota_server.routes

import at.fklab.ota_server.models.FirmwareReleaseInput
import at.fklab.ota_server.services.FirmwareReleaseService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.firmwareReleasesRoute(firmwareReleaseService: FirmwareReleaseService) {
    route("/firmwareReleases") {
        get {
            call.respond(firmwareReleaseService.getAll())
        }

        get("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val firmwareRelease = firmwareReleaseService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(firmwareRelease)
        }

        get("/{id}/download") {
            call.respond(HttpStatusCode.NotImplemented)
        }

        post {
            val firmwareRelease = call.receive<FirmwareReleaseInput>()
            call.respond(firmwareReleaseService.add(firmwareRelease))
        }

        put {
            val firmwareRelease = call.receive<FirmwareReleaseInput>()
            call.respond(firmwareReleaseService.update(firmwareRelease))
        }

        delete("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(firmwareReleaseService.delete(id))
        }
    }
}