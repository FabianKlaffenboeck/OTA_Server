package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class FirmwareReleasesRouteTest : ApiTestUtils() {

    @BeforeTest
    fun resetDB() {
        initDB()
        populateDB()
    }

    @Test
    fun testGetFirmwarereleases() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/firmwareReleases")
    }

    @Test
    fun testPostFirmwarereleases() = testApplication {
        application {
            module()
        }
        val response = client.post("$apiRoute/firmwareReleases")
    }

    @Test
    fun testPutFirmwarereleases() = testApplication {
        application {
            module()
        }
        val response = client.put("$apiRoute/firmwareReleases")
    }

    @Test
    fun testDeleteFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        val response = client.delete("$apiRoute/firmwareReleases/1")
    }

    @Test
    fun testGetFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/firmwareReleases/1")
    }
}