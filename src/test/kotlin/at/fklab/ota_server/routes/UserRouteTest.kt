package at.fklab.ota_server.routes

import at.fklab.ota_server.development.sampleUsers
import at.fklab.ota_server.models.User
import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.configureDatabases
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf
import kotlin.test.Test

class UserRouteTest : ApiTestUtils() {


    @Before
    fun resetDB() = testApplication {
        application {
            configureDatabases("jdbc:sqlite:TestDB", "root", "", true, true, true)
        }
    }

    @Test
    fun testGetUsers() = testApplication {
        application {
            module()
        }

        val response = client.get("$apiRoute/users")
        val users: List<User> = Gson().fromJson(response.bodyAsText(), typeOf<List<User>>().javaType)

        assertEquals(2, users.size)
    }

    @Test
    fun testPostUsers() = testApplication {
        application {
            module()
        }

        val sampleUser = sampleUsers[0].copy(id = null, info = "coolInfo01")

        val response = client.post("$apiRoute/users") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleUser))
        }

        val responseUser: User = Gson().fromJson(response.bodyAsText(), User::class.java)

        assertEquals(3, responseUser.id)
        assertEquals(sampleUser.info, responseUser.info)
    }

    @Test
    fun testPutUsers() = testApplication {
        application {
            module()
        }

        val sampleUser = sampleUsers[0].copy(info = "coolInfo01")

        val response = client.put("$apiRoute/users") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleUser))
        }

        val responseUser: User = Gson().fromJson(response.bodyAsText(), User::class.java)

        assertEquals(1, responseUser.id)
        assertEquals(sampleUser.info, responseUser.info)
    }

    @Test
    fun testDeleteUsersId() = testApplication {
        application {
            module()
        }
        client.delete("$apiRoute/users/1")

        val response = client.get("$apiRoute/users")
        val users: List<User> = Gson().fromJson(response.bodyAsText(), typeOf<List<User>>().javaType)

        assertEquals(1, users.size)
    }

    @Test
    fun testGetUsersId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/users/1")

        val user: User = Gson().fromJson(response.bodyAsText(), User::class.java)

        assertEquals(1, user.id)
    }
}