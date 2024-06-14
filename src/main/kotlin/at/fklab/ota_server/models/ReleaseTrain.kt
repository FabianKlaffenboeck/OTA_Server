package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object ReleaseTrains : IntIdTable("releaseTrains") {
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class ReleaseTrainEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ReleaseTrainEntity>(ReleaseTrains)

    val firmwareReleases by FirmwareReleaseEntity referrersOn FirmwareReleases.releaseTrain

    var info by ReleaseTrains.info

    var updatedAt by ReleaseTrains.updatedAt
    var updatedBy by ReleaseTrains.updatedBy
    var deletedAt by ReleaseTrains.deletedAt
    var deletedBy by ReleaseTrains.deletedBy

    fun toReleaseTrain() = ReleaseTrain(
        id.value, firmwareReleases.map { it.toFirmwareRelease() }, info
    )
}

class ReleaseTrain(
    var id: Int?, var firmwareReleases: List<FirmwareRelease>?, var info: String?
)

class ReleaseTrainInput(
    var id: Int?, var info: String?
)
