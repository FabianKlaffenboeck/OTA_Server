package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object AccessEvents : UUIDTable("AccessEvents") {
    val accessDevice_id = reference("accessDevice_id", AccessDevices)

    val eventTime = datetime("eventTime")
}

class AccessEventEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccessEventEntity>(AccessEvents)

    var accessDevice by AccessDeviceEntity referencedOn AccessEvents.accessDevice_id

    var eventTime by AccessEvents.eventTime

    fun toAccessEvent() = AccessEvent(
        id.value, accessDevice.toAccessDevice(), eventTime
    )
}

class AccessEvent(
    var id: UUID?, var accessDevice: AccessDevice, var eventTime: LocalDateTime
)