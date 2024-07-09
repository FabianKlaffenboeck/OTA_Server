package at.fklab.ota_server.routes

import at.fklab.ota_server.infra.AUTH_GENERAL
import at.fklab.ota_server.models.ReleaseTrainInput
import at.fklab.ota_server.services.ReleaseTrainService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.releaseTrainsRoute(releaseTrainService: ReleaseTrainService) {
    authenticate(AUTH_GENERAL) {
        route("/releaseTrains") {
            get {
                call.respond(releaseTrainService.getAll())
            }

            get("/{id}") {
                val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
                val releaseTrain = releaseTrainService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(releaseTrain)
            }

            post {
                val releaseTrain = call.receive<ReleaseTrainInput>()
                call.respond(releaseTrainService.add(releaseTrain))
            }

            put {
                val releaseTrain = call.receive<ReleaseTrainInput>()
                call.respond(releaseTrainService.update(releaseTrain))
            }

            delete("/{id}") {
                val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
                call.respond(releaseTrainService.delete(id))
            }
        }
    }
}