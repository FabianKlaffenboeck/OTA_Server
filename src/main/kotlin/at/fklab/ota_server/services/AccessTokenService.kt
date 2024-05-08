package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class AccessTokenService {

    fun getAll(): List<AccessDevice> = transaction {
        val query = Op.build { AccessDevices.deletedAt.isNull() }
        AccessDeviceEntity.find(query).map(AccessDeviceEntity::toAccessDevice)
    }

    fun getById(id: UUID): AccessDevice? = transaction {
        AccessDeviceEntity.find {
            AccessDevices.id eq id
        }.firstOrNull()?.toAccessDevice()
    }

    fun add(accessToken: AccessToken): AccessToken = transaction {
        AccessTokenEntity.new {

            accessDevice = accessToken.id?.let { AccessDeviceEntity.findById(it) }!!
            allowedRead = accessToken.allowedRead
            info = accessToken.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toAccessToken()
    }

    fun update(accessToken: AccessToken): AccessToken = transaction {
        val notNullId = accessToken.id!!

        AccessTokenEntity[notNullId].accessDevice = accessToken.id?.let { AccessDeviceEntity.findById(it) }!!
        AccessTokenEntity[notNullId].allowedRead = accessToken.allowedRead
        AccessTokenEntity[notNullId].info = accessToken.info

        AccessTokenEntity[notNullId].updatedAt = LocalDateTime.now()
        AccessTokenEntity[notNullId].updatedBy = ""
        AccessTokenEntity[notNullId].toAccessToken()
    }

    fun delete(id: UUID) = transaction {
        AccessTokenEntity[id].deletedAt = LocalDateTime.now()
    }
}