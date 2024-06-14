package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserRouteTest {

    @BeforeTest
    fun resetDB(){
        initDB()
        populateDB()
    }

    @Test
    fun testGetUsers() = testApplication {
        application {
            module()
        }
        client.get("/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostUsers() = testApplication {
        application {
            module()
        }
        client.post("/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutUsers() = testApplication {
        application {
            module()
        }
        client.put("/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteUsersId() = testApplication {
        application {
            module()
        }
        client.delete("/users/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetUsersId() = testApplication {
        application {
            module()
        }
        client.get("/users/{id}").apply {
            TODO("Please write your test here")
        }
    }
}