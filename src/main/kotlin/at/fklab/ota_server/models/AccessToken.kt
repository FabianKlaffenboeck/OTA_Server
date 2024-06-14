package at.fklab.ota_server.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object AccessTokens : IntIdTable("accessTokens") {
    val tokenString = text("tokenString")
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class AccessTokenEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccessTokenEntity>(AccessTokens)

    var tokenString by AccessTokens.tokenString
    var info by AccessTokens.info

    var updatedAt by AccessTokens.updatedAt
    var updatedBy by AccessTokens.updatedBy
    var deletedAt by AccessTokens.deletedAt
    var deletedBy by AccessTokens.deletedBy

    fun toAccessToken() = AccessToken(
        id.value, tokenString, info
    )
}

@Serializable
data class AccessToken(
    var id: Int?, var tokenString: String, var info: String?
)