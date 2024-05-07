package at.fklab.ota_server.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object AccessEvents : UUIDTable("AccessEvents") {
    val device_id = reference("device_id", AccessDevices)
    val eventTime = datetime("eventTime")
}

class AccessEventEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccessEventEntity>(AccessEvents)

    private var eventTime by AccessEvents.eventTime

    fun toAccessEvent() = AccessEvent(
        id.value, eventTime
    )
}

class AccessEvent(
    var id: UUID?, var eventTime: LocalDateTime
)