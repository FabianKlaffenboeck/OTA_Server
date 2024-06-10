package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object FirmwareReleases : IntIdTable("firmwareReleases") {
    val info = text("info").nullable()
    val releaseTrain = reference("releaseTrain", ReleaseTrains)

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class FirmwareReleaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FirmwareReleaseEntity>(FirmwareReleases)

    var info by FirmwareReleases.info
    var releaseTrain by ReleaseTrainEntity referencedOn FirmwareReleases.releaseTrain

    var updatedAt by FirmwareReleases.updatedAt
    var updatedBy by FirmwareReleases.updatedBy
    var deletedAt by FirmwareReleases.deletedAt
    var deletedBy by FirmwareReleases.deletedBy

    fun toFirmwareRelease() = FirmwareRelease(
        id.value, info
    )
}

class FirmwareRelease(
    var id: Int?, var info: String?
)