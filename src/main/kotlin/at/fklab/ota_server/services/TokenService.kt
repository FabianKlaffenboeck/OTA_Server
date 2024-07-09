package at.fklab.ota_server.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenService(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    private val myRealm: String
) {

    fun generateNewToken(): String {
        val token = JWT.create().withAudience(audience).withIssuer(issuer).withClaim("username", "user.login")
            .withExpiresAt(Date(System.currentTimeMillis() + 60000)).sign(Algorithm.HMAC256(secret))

        return token
    }

}