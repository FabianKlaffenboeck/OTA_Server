package at.fklab.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*


object AccessDevices : UUIDTable("AccessDevices") {
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class AccessDeviceEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccessDeviceEntity>(AccessDevices)

    private val tokens by AccessTokenEntity referrersOn AccessTokens.device_id
    private val accessEventEntity by AccessEventEntity referrersOn AccessEvents.device_id
    private var info by AccessDevices.info

    var updatedAt by AccessDevices.updatedAt
    var updatedBy by AccessDevices.updatedBy
    var deletedAt by AccessDevices.deletedAt
    var deletedBy by AccessDevices.deletedBy

    fun toAccessDevice() = AccessDevice(
        id.value, tokens.iterator().asSequence().toList(), accessEventEntity.iterator().asSequence().toList(), info
    )
}

class AccessDevice(
    var id: UUID?, var tokens: List<AccessTokenEntity>, var accessEvent: List<AccessEventEntity>, var info: String?
)