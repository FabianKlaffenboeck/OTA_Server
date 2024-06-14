package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeviceRouteTest : ApiTestUtils() {


    @Test
    fun testGetDevices() = testApplication {
        application {
            module()
            initDB()
            populateDB()
        }
        val response = client.get("$apiRoute/devices")

        assertEquals(HttpStatusCode.OK, response.status)
        println(response.bodyAsText())
    }

    @Test
    fun testPostDevices() = testApplication {
        application {
            module()
            initDB()
            populateDB()
        }
        val response = client.post("$apiRoute/devices")
    }

    @Test
    fun testPutDevices() = testApplication {
        application {
            module()
            initDB()
            populateDB()
        }
        val response = client.put("$apiRoute/devices")
    }

    @Test
    fun testDeleteDevicesId() = testApplication {
        application {
            module()
            initDB()
            populateDB()
        }
        val response = client.delete("$apiRoute/devices/1")
    }

    @Test
    fun testGetDevicesId() = testApplication {
        application {
            module()
            initDB()
            populateDB()
        }
        val response = client.get("$apiRoute/devices/1")
    }
}