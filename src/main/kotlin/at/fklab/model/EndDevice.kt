package at.fklab.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object EndDevices : IntIdTable("Trays") {
    val name = varchar("name", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class EndDeviceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EndDeviceEntity>(EndDevices)

    var name by EndDevices.name

    var updatedAt by EndDevices.updatedAt
    var deletedAt by EndDevices.deletedAt

    fun toEndDevice() = EndDevice(
        id.value,
        name
    )
}

class EndDevice(
    var id: Int?,
    var name: String
)