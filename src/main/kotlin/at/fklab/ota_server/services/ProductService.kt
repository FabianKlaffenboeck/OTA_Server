package at.fklab.ota_server.services

import at.fklab.ota_server.models.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ProductService {

    fun getAll(): List<Product> = transaction {
        val query = Op.build { Products.deletedAt.isNull() }
        ProductEntity.find(query).map(ProductEntity::toProduct)
    }

    fun getById(id: Int): Product? = transaction {
        ProductEntity.find {
            Products.id eq id
        }.firstOrNull()?.toProduct()
    }

    fun add(product: Product): Product = transaction {
        ProductEntity.new {
            info = product.info

            updatedAt = LocalDateTime.now()
            updatedBy = ""
        }.toProduct()
    }

    fun update(product: Product):Product = transaction {
        val notNullId = product.id!!

        ProductEntity[notNullId].info = product.info

        ProductEntity[notNullId].updatedAt = LocalDateTime.now()
        ProductEntity[notNullId].updatedBy = ""
        ProductEntity[notNullId].toProduct()
    }

    fun delete(id: Int) = transaction {
        ProductEntity[id].deletedAt = LocalDateTime.now()
    }
}