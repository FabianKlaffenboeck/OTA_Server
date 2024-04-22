package at.fklab.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*


object EndDevices : UUIDTable("EndDevices") {
    val info = varchar("info", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class EndDeviceEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EndDeviceEntity>(EndDevices)

    var info by EndDevices.info

    var updatedAt by EndDevices.updatedAt
    var deletedAt by EndDevices.deletedAt

    fun toEndDevice() = EndDevice(
        id.value, info
    )
}

class EndDevice(
    var id: UUID?,
    var info: String
)