package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Products : IntIdTable("products") {
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class ProductEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductEntity>(Products)

    var info by Products.info

    var updatedAt by Products.updatedAt
    var updatedBy by Products.updatedBy
    var deletedAt by Products.deletedAt
    var deletedBy by Products.deletedBy

    fun toProduct() = Product(
        id.value, info
    )
}

class Product(
    var id: Int?, var info: String?
)