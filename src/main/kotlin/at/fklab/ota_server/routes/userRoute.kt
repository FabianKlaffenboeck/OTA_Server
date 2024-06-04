package at.fklab.ota_server.routes

import at.fklab.ota_server.models.Ota_User
import at.fklab.ota_server.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(userService: UserService) {
    route("/users") {
        get {
            call.respond(userService.getAll())
        }

        get("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val user = userService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(user)
        }

        post {
            val user = call.receive<Ota_User>()
            call.respond(userService.add(user))
        }

        put {
            val user = call.receive<Ota_User>()
            call.respond(userService.update(user))
        }

        delete("/{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(userService.delete(id))
        }
    }
}