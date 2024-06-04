package at.fklab.ota_server.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Users : IntIdTable("users") {
    val info = text("info").nullable()

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var info by Users.info

    var updatedAt by Users.updatedAt
    var updatedBy by Users.updatedBy
    var deletedAt by Users.deletedAt
    var deletedBy by Users.deletedBy

    fun toAccessDevice() = Ota_User(
        id.value, info
    )
}

class Ota_User(
    var id: Int?, var info: String?
)