package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class FirmwareReleaseService {

    fun getAll(): List<FirmwareRelease> = transaction {
        val query = Op.build { FirmwareReleases.deletedAt.isNull() }
        FirmwareReleaseEntity.find(query).map(FirmwareReleaseEntity::toFirmwareRelease)
    }

    fun getById(id: Int): FirmwareRelease? = transaction {
        FirmwareReleaseEntity.find {
            FirmwareReleases.id eq id
        }.firstOrNull()?.toFirmwareRelease()
    }

    fun add(firmwareRelease: FirmwareReleaseInput): FirmwareRelease = transaction {
        FirmwareReleaseEntity.new {

            releaseTrain = firmwareRelease.releaseTrain.id?.let { ReleaseTrainEntity.findById(it) }!!
            info = firmwareRelease.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toFirmwareRelease()
    }

    fun update(firmwareRelease: FirmwareReleaseInput): FirmwareRelease = transaction {
        val notNullId = firmwareRelease.id!!


        FirmwareReleaseEntity[notNullId].releaseTrain = firmwareRelease.releaseTrain.id?.let {
            ReleaseTrainEntity.findById(it)
        }!!

        FirmwareReleaseEntity[notNullId].info = firmwareRelease.info

        FirmwareReleaseEntity[notNullId].updatedAt = LocalDateTime.now()
        FirmwareReleaseEntity[notNullId].updatedBy = ""
        FirmwareReleaseEntity[notNullId].toFirmwareRelease()
    }

    fun delete(id: Int) = transaction {
        FirmwareReleaseEntity[id].deletedAt = LocalDateTime.now()
    }
}