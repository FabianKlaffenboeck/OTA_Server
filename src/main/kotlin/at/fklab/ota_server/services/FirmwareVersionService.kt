package at.fklab.ota_server.services

import at.fklab.ota_server.models.AccessDeviceEntity
import at.fklab.ota_server.models.FirmwareVersion
import at.fklab.ota_server.models.FirmwareVersionEntity
import at.fklab.ota_server.models.FirmwareVersions
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class FirmwareVersionService {

    fun getAll(deviceId: String? = null): List<FirmwareVersion> = transaction {
        var query = Op.build { FirmwareVersions.deletedAt.isNull() }

        if (deviceId != null) {
            query = query.and { FirmwareVersions.hardwareDevice eq UUID.fromString(deviceId) }
        }

        FirmwareVersionEntity.find(query).map(FirmwareVersionEntity::toFirmwareVersion)
    }

    fun getById(id: UUID): FirmwareVersion? = transaction {
        FirmwareVersionEntity.find {
            FirmwareVersions.id eq id
        }.firstOrNull()?.toFirmwareVersion()
    }

    fun add(firmwareVersion: FirmwareVersion): FirmwareVersion = transaction {
        FirmwareVersionEntity.new {

            versionNr = firmwareVersion.versionNr
            gitCommitId = firmwareVersion.gitCommitId
            hardwareDevice = firmwareVersion.hardwareDevice.id?.let { AccessDeviceEntity.findById(it) }!!
            updateCategory = firmwareVersion.updateCategory
            firmwareFileName = firmwareVersion.firmwareFileName
            info = firmwareVersion.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toFirmwareVersion()
    }

    fun update(firmwareVersion: FirmwareVersion): FirmwareVersion = transaction {
        val notNullId = firmwareVersion.id!!

        FirmwareVersionEntity[notNullId].versionNr = firmwareVersion.versionNr
        FirmwareVersionEntity[notNullId].gitCommitId = firmwareVersion.gitCommitId
        FirmwareVersionEntity[notNullId].hardwareDevice =
            firmwareVersion.hardwareDevice.id?.let { AccessDeviceEntity.findById(it) }!!
        FirmwareVersionEntity[notNullId].updateCategory = firmwareVersion.updateCategory
        FirmwareVersionEntity[notNullId].firmwareFileName = firmwareVersion.firmwareFileName
        FirmwareVersionEntity[notNullId].info = firmwareVersion.info

        FirmwareVersionEntity[notNullId].updatedAt = LocalDateTime.now()
        FirmwareVersionEntity[notNullId].updatedBy = ""

        FirmwareVersionEntity[notNullId].toFirmwareVersion()
    }

    fun delete(id: UUID) = transaction {
        FirmwareVersionEntity[id].deletedAt = LocalDateTime.now()
    }
}