package at.fklab.ota_server.services

import at.fklab.ota_server.models.FirmwareVersion
import at.fklab.ota_server.models.FirmwareVersionEntity
import at.fklab.ota_server.models.FirmwareVersions
import at.fklab.ota_server.models.ProductEntity
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class FirmwareVersionService {

    fun getAll(): List<FirmwareVersion> = transaction {
        val query = Op.build { FirmwareVersions.deletedAt.isNull() }
        FirmwareVersionEntity.find(query).map(FirmwareVersionEntity::toFirmwareVersion)
    }

    fun getById(id: Int): FirmwareVersion? = transaction {
        FirmwareVersionEntity.find {
            FirmwareVersions.id eq id
        }.firstOrNull()?.toFirmwareVersion()
    }

    fun add(firmwareVersion: FirmwareVersion): FirmwareVersion = transaction {
        FirmwareVersionEntity.new {
            binaryFileName = firmwareVersion.binaryFileName
            associatedProduct = firmwareVersion.associatedProduct?.id?.let {
                ProductEntity.findById(
                    it
                )
            }
            version = firmwareVersion.version
            info = firmwareVersion.info
            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toFirmwareVersion()
    }


    fun update(firmwareVersion: FirmwareVersion): FirmwareVersion = transaction {
        val notNullId = firmwareVersion.id!!

        FirmwareVersionEntity[notNullId].binaryFileName = firmwareVersion.binaryFileName
        FirmwareVersionEntity[notNullId].associatedProduct = firmwareVersion.associatedProduct?.id?.let {
            ProductEntity.findById(
                it
            )
        }
        FirmwareVersionEntity[notNullId].version = firmwareVersion.version
        FirmwareVersionEntity[notNullId].info = firmwareVersion.info

        FirmwareVersionEntity[notNullId].updatedAt = LocalDateTime.now()
        FirmwareVersionEntity[notNullId].updatedBy = ""
        FirmwareVersionEntity[notNullId].toFirmwareVersion()
    }

    fun delete(id: Int) = transaction {
        FirmwareVersionEntity[id].deletedAt = LocalDateTime.now()
    }
}