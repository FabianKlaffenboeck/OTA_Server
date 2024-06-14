package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class FirmwareReleasesRouteTest {

    @BeforeTest
    fun resetDB(){
        initDB()
        populateDB()
    }

    @Test
    fun testGetFirmwarereleases() = testApplication {
        application {
            module()
        }
        client.get("/firmwareReleases").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostFirmwarereleases() = testApplication {
        application {
            module()
        }
        client.post("/firmwareReleases").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutFirmwarereleases() = testApplication {
        application {
            module()
        }
        client.put("/firmwareReleases").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        client.delete("/firmwareReleases/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        client.get("/firmwareReleases/{id}").apply {
            TODO("Please write your test here")
        }
    }
}