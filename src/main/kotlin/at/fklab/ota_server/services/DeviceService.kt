package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class DeviceService {

    fun getAll(): List<Device> = transaction {
        val query = Op.build { Devices.deletedAt.isNull() }
        DeviceEntity.find(query).map(DeviceEntity::toDevice)
    }

    fun getById(id: Int): Device? = transaction {
        DeviceEntity.find {
            Devices.id eq id
        }.firstOrNull()?.toDevice()
    }

    fun add(device: Device): Device = transaction {
        DeviceEntity.new {
            accessToken = device.accessToken.id?.let { AccessTokenEntity.findById(it) }!!
            info = device.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toDevice()
    }

    fun update(device: Device): Device = transaction {
        val notNullId = device.id!!

        DeviceEntity[notNullId].accessToken = device.accessToken.id?.let { AccessTokenEntity.findById(it) }!!
        DeviceEntity[notNullId].info = device.info

        DeviceEntity[notNullId].updatedAt = LocalDateTime.now()
        DeviceEntity[notNullId].updatedBy = ""
        DeviceEntity[notNullId].toDevice()
    }

    fun delete(id: Int) = transaction {
        DeviceEntity[id].deletedAt = LocalDateTime.now()
    }
}