package at.fklab.ota_server.services

import at.fklab.ota_server.models.AccessTokenEntity
import at.fklab.ota_server.models.User
import at.fklab.ota_server.models.UserEntity
import at.fklab.ota_server.models.Users
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class UserService {

    fun getAll(): List<User> = transaction {
        val query = Op.build { Users.deletedAt.isNull() }
        UserEntity.find(query).map(UserEntity::toUser)
    }

    fun getById(id: Int): User? = transaction {
        UserEntity.find {
            Users.id eq id
        }.firstOrNull()?.toUser()
    }

    fun add(user: User): User = transaction {
        UserEntity.new {
            accessToken = user.accessToken.id?.let { AccessTokenEntity.findById(it) }!!
            info = user.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toUser()
    }

    fun update(user: User): User = transaction {
        val notNullId = user.id!!

        UserEntity[notNullId].accessToken = user.accessToken.id?.let { AccessTokenEntity.findById(it) }!!
        UserEntity[notNullId].info = user.info

        UserEntity[notNullId].updatedAt = LocalDateTime.now()
        UserEntity[notNullId].updatedBy = ""
        UserEntity[notNullId].toUser()
    }

    fun delete(id: Int) = transaction {
        UserEntity[id].deletedAt = LocalDateTime.now()
    }
}