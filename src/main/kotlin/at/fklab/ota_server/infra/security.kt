package at.fklab.ota_server.infra

import at.fklab.ota_server.services.TokenService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

const val AUTH_ALL = "auth-general"

fun Application.configureSecurity(
    tokenService: TokenService, secret: String, issuer: String, audience: String, myRealm: String
) {
    install(Authentication) {

        jwt(AUTH_ALL) {

            authHeader { call ->
                val header = call.request.headers[HttpHeaders.Authorization] ?: return@authHeader null

                val decodedToken = String(Base64.getDecoder().decode(header.removePrefix("Bearer ")))

                try {
                    parseAuthorizationHeader("Bearer $decodedToken")
                } catch (cause: IllegalArgumentException) {
                    null
                }

            }

            verifier(JWT.require(Algorithm.HMAC256(secret)).withAudience(audience).withIssuer(issuer).build())
            validate { credential ->
                if (tokenService.isRevoked(credential.payload.getClaim("tokenId").asInt())) {
                    null
                } else {
                    JWTPrincipal(credential.payload)
                }
            }
        }

    }
}
