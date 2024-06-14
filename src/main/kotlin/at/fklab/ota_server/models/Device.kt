package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Devices : IntIdTable("devices") {

    val accessToken = reference("accessToken", AccessTokens)
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class DeviceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DeviceEntity>(Devices)

    var accessToken by AccessTokenEntity referencedOn Devices.accessToken
    var info by Devices.info

    var updatedAt by Devices.updatedAt
    var updatedBy by Devices.updatedBy
    var deletedAt by Devices.deletedAt
    var deletedBy by Devices.deletedBy

    fun toDevice() = Device(
        id.value, accessToken.toAccessToken(), info
    )
}

class Device(
    var id: Int?, var accessToken: AccessToken, var info: String?
)