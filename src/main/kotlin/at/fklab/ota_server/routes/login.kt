package at.fklab.ota_server.routes

import at.fklab.ota_server.models.User
import at.fklab.ota_server.services.UserService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


fun Route.login(userService: UserService, secret: String, issuer: String, audience: String, myRealm: String) {
    post("/login") {
        val user = call.receive<User>()

        //TODO ValidateUser

        val token = JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.login)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))

        call.respond(hashMapOf("token" to token))
    }
}