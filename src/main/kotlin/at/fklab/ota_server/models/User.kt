package at.fklab.ota_server.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Users : IntIdTable("users") {

    val login = varchar("login", 10).default("")
    val password = varchar("password", 30).default("")
    val firstname = varchar("firstname", 255).default("")
    val lastname = varchar("lastname", 255).default("")
    val description = varchar("description", 255).default("")

    val updatedAt = datetime("updatedAt").nullable()
    val updatedBy = text("updatedBy").nullable()
    val deletedAt = datetime("deletedAt").nullable()
    val deletedBy = text("deletedBy").nullable()
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var login by Users.login
    var password by Users.password
    var firstname by Users.firstname
    var lastname by Users.lastname
    var description by Users.description

    var updatedAt by Users.updatedAt
    var updatedBy by Users.updatedBy
    var deletedAt by Users.deletedAt
    var deletedBy by Users.deletedBy

    fun toUser() = User(
        id.value, login, password, firstname, lastname, description
    )
}

@Serializable
data class User(
    var id: Int?,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    var description: String
)