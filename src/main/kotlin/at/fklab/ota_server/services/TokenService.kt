package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class TokenService {

    fun getAll(): List<AccessToken> = transaction {
        val query = Op.build { Users.deletedAt.isNull() }
        AccessTokenEntity.find(query).map(AccessTokenEntity::toAccessToken)
    }

    fun getById(id: Int): AccessToken? = transaction {
        AccessTokenEntity.find {
            AccessTokens.id eq id
        }.firstOrNull()?.toAccessToken()
    }

    fun add(accessToken: AccessToken): AccessToken = transaction {
        AccessTokenEntity.new {

            tokenString = accessToken.tokenString
            info = accessToken.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toAccessToken()
    }

    fun update(accessToken: AccessToken): AccessToken = transaction {
        val notNullId = accessToken.id!!

        AccessTokenEntity[notNullId].tokenString = accessToken.tokenString
        AccessTokenEntity[notNullId].info = accessToken.info

        AccessTokenEntity[notNullId].updatedAt = LocalDateTime.now()
        AccessTokenEntity[notNullId].updatedBy = ""
        AccessTokenEntity[notNullId].toAccessToken()
    }

    fun delete(id: Int) = transaction {
        AccessTokenEntity[id].deletedAt = LocalDateTime.now()
    }
}