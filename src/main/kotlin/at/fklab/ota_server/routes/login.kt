package at.fklab.ota_server.routes

import at.fklab.ota_server.infra.AUTH_ALL
import at.fklab.ota_server.models.User
import at.fklab.ota_server.services.TokenService
import at.fklab.ota_server.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.login(
    userService: UserService,
    tokenService: TokenService,
) {
    post("/login") {
        val user = call.receive<User>()

        val foundUser = userService.getAll().find { (it.login == user.login) && (it.password == user.password) }

        if (foundUser == null) {
            return@post call.respond(HttpStatusCode.Unauthorized)
        }

        val token = tokenService.generateNewToken(user.login, true)

        call.respond(hashMapOf("token" to token))
    }

    authenticate(AUTH_ALL) {
        post("/apiToken") {
            val user = call.receive<User>()

            val foundUser = userService.getAll().find { (it.login == user.login) && (it.password == user.password) }

            if (foundUser == null) {
                return@post call.respond(HttpStatusCode.Unauthorized)
            }

            val token = tokenService.generateNewToken(user.login, false)

            call.respond(hashMapOf("token" to token))
        }
    }
}