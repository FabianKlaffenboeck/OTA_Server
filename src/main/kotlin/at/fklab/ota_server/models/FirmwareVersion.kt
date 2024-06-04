package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object FirmwareVersions : IntIdTable("firmwareVersions") {
    val binaryFileName = text("binaryFileName")
    val associatedProduct_id = reference("associatedProduct_id", Products).nullable()

    val version = text("version")
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class FirmwareVersionEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FirmwareVersionEntity>(FirmwareVersions)

    var associatedProduct by ProductEntity optionalReferencedOn FirmwareVersions.associatedProduct_id

    var binaryFileName by FirmwareVersions.binaryFileName
    var version by FirmwareVersions.version
    var info by FirmwareVersions.info

    var updatedAt by FirmwareVersions.updatedAt
    var updatedBy by FirmwareVersions.updatedBy
    var deletedAt by FirmwareVersions.deletedAt
    var deletedBy by FirmwareVersions.deletedBy

    fun toAccessDevice() = FirmwareVersion(
        id.value, binaryFileName, associatedProduct?.toProduct(), version, info
    )
}

class FirmwareVersion(
    var id: Int?, var binaryFileName: String, val associatedProduct: Product?, var version: String, var info: String?
)