package at.fklab.ota_server.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object FirmwareReleases : IntIdTable("firmwareReleases") {
    val version = text("version").default("")
    val buildHash = text("buildHash").default("")
    val pipeLineId = text("pipeLineId").default("")
    val commitHash = text("commitHash").default("")
    val info = text("info").nullable()
    val releaseTrain = reference("releaseTrain", ReleaseTrains)

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class FirmwareReleaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FirmwareReleaseEntity>(FirmwareReleases)

    var version by FirmwareReleases.version
    var buildHash by FirmwareReleases.buildHash
    var pipeLineId by FirmwareReleases.pipeLineId
    var commitHash by FirmwareReleases.commitHash
    var info by FirmwareReleases.info
    var releaseTrain by ReleaseTrainEntity referencedOn FirmwareReleases.releaseTrain

    var updatedAt by FirmwareReleases.updatedAt
    var updatedBy by FirmwareReleases.updatedBy
    var deletedAt by FirmwareReleases.deletedAt
    var deletedBy by FirmwareReleases.deletedBy

    fun toFirmwareRelease() = FirmwareRelease(
        id.value, version, buildHash, pipeLineId, commitHash, info
    )
}

@Serializable
data class FirmwareRelease(
    var id: Int?,
    var version: String,
    var buildHash: String,
    var pipeLineId: String,
    var commitHash: String,
    var info: String?
)

@Serializable
data class FirmwareReleaseInput(
    var id: Int?,
    var version: String,
    var buildHash: String,
    var pipeLineId: String,
    var commitHash: String,
    var info: String?,
    var releaseTrain: ReleaseTrainInput,
)
