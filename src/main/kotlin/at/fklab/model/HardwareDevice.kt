package at.fklab.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*


object HardwareDevices : UUIDTable("HardwareDevices") {
    val info = text("info")

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class HardwareDeviceEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<HardwareDeviceEntity>(HardwareDevices)

    var info by HardwareDevices.info

    var updatedAt by HardwareDevices.updatedAt
    var updatedBy by HardwareDevices.updatedBy
    var deletedAt by HardwareDevices.deletedAt
    var deletedBy by HardwareDevices.deletedBy

    fun toHardwareDevice() = HardwareDevice(
        id.value, info
    )
}

class HardwareDevice(
    var id: UUID?, var info: String
)