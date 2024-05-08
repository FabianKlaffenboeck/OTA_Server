package at.fklab.ota_server.services

import at.fklab.ota_server.models.AccessDevice
import at.fklab.ota_server.models.AccessDeviceEntity
import at.fklab.ota_server.models.AccessDevices
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class AccessDeviceService {

    fun getAll(): List<AccessDevice> = transaction {
        val query = Op.build { AccessDevices.deletedAt.isNull() }
        AccessDeviceEntity.find(query).map(AccessDeviceEntity::toAccessDevice)
    }

    fun getById(id: UUID): AccessDevice? = transaction {
        AccessDeviceEntity.find {
            AccessDevices.id eq id
        }.firstOrNull()?.toAccessDevice()
    }

    fun add(accessDevice: AccessDevice): AccessDevice {
        val newDevice = transaction {
            AccessDeviceEntity.new {
                info = accessDevice.info

                updatedAt = LocalDateTime.now()
                updatedBy = ""
            }.toAccessDevice()
        }

        return newDevice
    }

    fun update(accessDevice: AccessDevice): AccessDevice = transaction {
        val notNullId = accessDevice.id!!

        AccessDeviceEntity[notNullId].info = accessDevice.info

        AccessDeviceEntity[notNullId].updatedAt = LocalDateTime.now()
        AccessDeviceEntity[notNullId].updatedBy = ""
        AccessDeviceEntity[notNullId].toAccessDevice()
    }

    fun delete(id: UUID) = transaction {
        AccessDeviceEntity[id].deletedAt = LocalDateTime.now()
    }
}