package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class AccessTokenService {

    fun getAll(deviceId: String? = null): List<AccessDevice> = transaction {
        var query = Op.build { AccessDevices.deletedAt.isNull() }

        if (deviceId != null) {
            query = query.and { AccessTokens.accessDevice_id eq UUID.fromString(deviceId) }
        }

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

    fun add(deviceId: UUID): AccessToken = transaction {
        AccessTokenEntity.new {

            accessDevice = AccessDeviceEntity.findById(deviceId)!!
            allowedRead = true
            info = ""

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