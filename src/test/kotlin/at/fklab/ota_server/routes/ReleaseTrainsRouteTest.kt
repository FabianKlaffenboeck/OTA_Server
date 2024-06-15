package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import at.fklab.ota_server.plugins.initDB
import at.fklab.ota_server.plugins.populateDB
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ReleaseTrainsRouteTest : ApiTestUtils() {

//    @BeforeTest
//    fun resetDB() {
//        initDB()
//        populateDB()
//    }

    @Test
    fun testGetReleasetrains() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/releaseTrains")
    }

    @Test
    fun testPostReleasetrains() = testApplication {
        application {
            module()
        }
        val response = client.post("$apiRoute/releaseTrains")
    }

    @Test
    fun testPutReleasetrains() = testApplication {
        application {
            module()
        }
        val response = client.put("$apiRoute/releaseTrains")
    }

    @Test
    fun testDeleteReleasetrainsId() = testApplication {
        application {
            module()
        }
        val response = client.delete("$apiRoute/releaseTrains/1")
    }

    @Test
    fun testGetReleasetrainsId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/releaseTrains/1")
    }
}