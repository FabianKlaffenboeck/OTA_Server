package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ReleaseTrainsRouteTest {

    @BeforeTest
    fun resetDB(){
        initDB()
        populateDB()
    }

    @Test
    fun testGetReleasetrains() = testApplication {
        application {
            module()
        }
        client.get("/releaseTrains").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostReleasetrains() = testApplication {
        application {
            module()
        }
        client.post("/releaseTrains").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutReleasetrains() = testApplication {
        application {
            module()
        }
        client.put("/releaseTrains").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteReleasetrainsId() = testApplication {
        application {
            module()
        }
        client.delete("/releaseTrains/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetReleasetrainsId() = testApplication {
        application {
            module()
        }
        client.get("/releaseTrains/{id}").apply {
            TODO("Please write your test here")
        }
    }
}