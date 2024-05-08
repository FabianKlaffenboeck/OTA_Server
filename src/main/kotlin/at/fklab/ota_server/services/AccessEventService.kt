package at.fklab.ota_server.services

import at.fklab.ota_server.models.AccessDeviceEntity
import at.fklab.ota_server.models.AccessEvent
import at.fklab.ota_server.models.AccessEventEntity
import at.fklab.ota_server.models.AccessEvents
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class AccessEventService {

    fun getAll(): List<AccessEvent> = transaction {
        AccessEventEntity.all().map(AccessEventEntity::toAccessEvent)
    }

    fun getById(id: UUID): AccessEvent? = transaction {
        AccessEventEntity.find {
            AccessEvents.id eq id
        }.firstOrNull()?.toAccessEvent()
    }

    fun add(accessDeviceId: UUID?): AccessEvent = transaction {
        AccessEventEntity.new {
            accessDevice = accessDeviceId?.let { AccessDeviceEntity.findById(it) }!!
            eventTime = LocalDateTime.now()
        }.toAccessEvent()
    }

//    fun update(accessToken: AccessToken): AccessToken = transaction {
//        val notNullId = accessToken.id!!
//
//        AccessTokenEntity[notNullId].accessDevice = accessToken.id?.let { AccessDeviceEntity.findById(it) }!!
//        AccessTokenEntity[notNullId].allowedRead = accessToken.allowedRead
//        AccessTokenEntity[notNullId].info = accessToken.info
//
//        AccessTokenEntity[notNullId].updatedAt = LocalDateTime.now()
//        AccessTokenEntity[notNullId].updatedBy = ""
//        AccessTokenEntity[notNullId].toAccessToken()
//    }
//
//    fun delete(id: UUID) = transaction {
//        AccessTokenEntity[id].deletedAt = LocalDateTime.now()
//    }
}