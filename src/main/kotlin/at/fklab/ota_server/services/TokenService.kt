package at.fklab.ota_server.services

import at.fklab.ota_server.models.TokenEntity
import at.fklab.ota_server.models.TokenState
import at.fklab.ota_server.models.Tokens
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class TokenService(
    private val secret: String, private val issuer: String, private val audience: String, private val myRealm: String
) {

    fun generateNewToken(): String {

        val tokenId = transaction {
            TokenEntity.new {
                tokenState = TokenState.VALID
                createdAt = LocalDateTime.now()
            }.id.value
        }

        val token = JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("tokenId", tokenId)
            .withClaim("username", "user.login")
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))

        return token
    }

    fun isRevoked(tokenID: Int): Boolean {
        val tokenEntity = transaction {
            TokenEntity.find(Tokens.id eq tokenID).firstOrNull()
        }

        if (tokenEntity != null) {
            return tokenEntity.tokenState == TokenState.REVOKED
        }

        return false
    }
}