package at.fklab.ota_server.services

import at.fklab.ota_server.models.Ota_User
import at.fklab.ota_server.models.UserEntity
import at.fklab.ota_server.models.Users
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserService {

    fun getAll(): List<Ota_User> = transaction {
        val query = Op.build { Users.deletedAt.isNull() }
        UserEntity.find(query).map(UserEntity::toAccessDevice)
    }

    fun getById(id: Int): Ota_User? = transaction {
        UserEntity.find {
            Users.id eq id
        }.firstOrNull()?.toAccessDevice()
    }

    fun add(accessDevice: Ota_User): Ota_User {
        val newDevice = transaction {
            UserEntity.new {
                info = accessDevice.info

                updatedAt = LocalDateTime.now()
                updatedBy = ""
            }.toAccessDevice()
        }

        return newDevice
    }

    fun update(accessDevice: Ota_User): Ota_User = transaction {
        val notNullId = accessDevice.id!!

        UserEntity[notNullId].info = accessDevice.info

        UserEntity[notNullId].updatedAt = LocalDateTime.now()
        UserEntity[notNullId].updatedBy = ""
        UserEntity[notNullId].toAccessDevice()
    }

    fun delete(id: Int) = transaction {
        UserEntity[id].deletedAt = LocalDateTime.now()
    }
}