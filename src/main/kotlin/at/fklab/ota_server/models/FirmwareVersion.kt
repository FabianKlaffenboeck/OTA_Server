package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

enum class UpdateCategory {
    Optional, Manual, Required, BreakingChanges
}

object FirmwareVersions : UUIDTable("FirmwareVersions") {
    val versionNr = text("versionNr")
    val gitCommitId = text("gitCommitId")
    val hardwareDevice = reference("hardwareDevice_id", AccessDevices)
    val updateCategory = enumeration("updateCategory", UpdateCategory::class)
    val firmwareFileName = text("firmwareFileName")
    val binaryHash = text("binaryHash")
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class FirmwareVersionEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<FirmwareVersionEntity>(FirmwareVersions)

    var versionNr by FirmwareVersions.versionNr
    var gitCommitId by FirmwareVersions.gitCommitId
    var hardwareDevice by AccessDeviceEntity referencedOn FirmwareVersions.hardwareDevice
    var updateCategory by FirmwareVersions.updateCategory
    var firmwareFileName by FirmwareVersions.firmwareFileName
    var binaryHash by FirmwareVersions.binaryHash
    var info by FirmwareVersions.info

    var updatedAt by FirmwareVersions.updatedAt
    var updatedBy by FirmwareVersions.updatedBy
    var deletedAt by FirmwareVersions.deletedAt
    var deletedBy by FirmwareVersions.deletedBy

    fun toFirmwareVersion() = FirmwareVersion(
        id.value,
        versionNr,
        gitCommitId,
        hardwareDevice.toAccessDevice(),
        updateCategory,
        firmwareFileName,
        binaryHash,
        info
    )
}

class FirmwareVersion(
    var id: UUID?,
    var versionNr: String,
    var gitCommitId: String,
    var hardwareDevice: AccessDevice,
    var updateCategory: UpdateCategory,
    var firmwareFileName: String,
    var binaryHash: String,
    var info: String?
)