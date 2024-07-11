package at.fklab.ota_server.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

enum class TokenState {
    VALID, REVOKED
}

object Tokens : IntIdTable("tokens") {

    val tokenState = enumeration("tokenState", TokenState::class)

    val createdAt = datetime("createdAt").nullable()
}

class TokenEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TokenEntity>(Tokens)

    var tokenState by Tokens.tokenState

    var createdAt by Tokens.createdAt

    fun isValid() = (tokenState == TokenState.VALID)

}

@Serializable
data class TokenDTO(
    var token: String
)
