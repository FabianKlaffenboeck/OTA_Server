package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserRouteTest : ApiTestUtils() {

    @BeforeTest
    fun resetDB() {
        initDB()
        populateDB()
    }

    @Test
    fun testGetUsers() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/users")
    }

    @Test
    fun testPostUsers() = testApplication {
        application {
            module()
        }
        val response = client.post("$apiRoute/users")
    }

    @Test
    fun testPutUsers() = testApplication {
        application {
            module()
        }
        val response = client.put("$apiRoute/users")
    }

    @Test
    fun testDeleteUsersId() = testApplication {
        application {
            module()
        }
        val response = client.delete("$apiRoute/users/1")
    }

    @Test
    fun testGetUsersId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/users/1")
    }
}