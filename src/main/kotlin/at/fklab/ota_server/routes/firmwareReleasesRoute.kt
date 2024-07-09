package at.fklab.ota_server.routes

import at.fklab.ota_server.infra.AUTH_GENERAL
import at.fklab.ota_server.models.FirmwareReleaseInput
import at.fklab.ota_server.services.FileService
import at.fklab.ota_server.services.FirmwareReleaseService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.firmwareReleasesRoute(firmwareReleaseService: FirmwareReleaseService, fileService: FileService) {
    authenticate(AUTH_GENERAL) {
        route("/firmwareReleases") {
            get {
                call.respond(firmwareReleaseService.getAll())
            }

            get("/{id}") {
                val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
                val firmwareRelease =
                    firmwareReleaseService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(firmwareRelease)
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

            get("/{id}/binary") {
                val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()

                val firmwareRelease =
                    firmwareReleaseService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                val file = fileService.get(firmwareRelease) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respondFile(file)
            }

            post("/{id}/binary") {
                val id: Int = (call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)).toInt()
                val multipart = call.receiveMultipart()


                val release = firmwareReleaseService.getById(id) ?: return@post call.respond(HttpStatusCode.NotFound)

                var hasBadFile = true

                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {

                        if (!((part.originalFileName == null) || (part.originalFileName == ""))) {
                            hasBadFile = false
                            fileService.save(part.streamProvider(), release)
                        }

                    }
                    part.dispose()
                }

                if (hasBadFile) {
                    return@post call.respond(HttpStatusCode.BadRequest)
                }

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}