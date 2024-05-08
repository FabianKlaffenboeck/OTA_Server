package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*


object AccessTokens : UUIDTable("AccessTokens") {
    val accessDevice_id = reference("accessDevice_id", AccessDevices)
    val allowedRead = bool("allowedRead")
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class AccessTokenEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccessTokenEntity>(AccessTokens)

    var accessDevice by AccessDeviceEntity referencedOn AccessTokens.accessDevice_id

    var allowedRead by AccessTokens.allowedRead
    var info by AccessTokens.info

    var updatedAt by AccessTokens.updatedAt
    var updatedBy by AccessTokens.updatedBy
    var deletedAt by AccessTokens.deletedAt
    var deletedBy by AccessTokens.deletedBy

    fun toAccessToken() = AccessToken(
        id.value, accessDevice.toAccessDevice(), allowedRead, info
    )
}

class AccessToken(
    var id: UUID?, var accessDevice: AccessDevice, var allowedRead: Boolean, var info: String?
)