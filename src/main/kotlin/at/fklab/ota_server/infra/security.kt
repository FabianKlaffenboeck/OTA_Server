package at.fklab.ota_server.infra

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val AUTH_GENERAL = "auth-general"

fun Application.configureSecurity(secret: String, issuer: String, audience: String, myRealm: String) {
    install(Authentication) {
        jwt(AUTH_GENERAL) {
            realm = myRealm
            verifier(JWT.require(Algorithm.HMAC256(secret)).withAudience(audience).withIssuer(issuer).build())
            validate { credential ->
                if (true) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
