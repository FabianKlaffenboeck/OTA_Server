package at.fklab.ota_server.routes

import at.fklab.ota_server.development.sampleReleaseTrains
import at.fklab.ota_server.models.ReleaseTrain
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class ReleaseTrainsRouteTest : ApiTestUtils() {

    @Test
    fun testGetReleasetrains() = testApplication {

        val response = client.get("$apiRoute/releaseTrains") {
            header("Authorization", "Bearer $testToken")
        }

        val releaseTrains: List<ReleaseTrain> = Gson().fromJson(response.bodyAsText())

        assertEquals(5, releaseTrains.size)
    }

    @Test
    fun testPostReleasetrains() = testApplication {

        val sampleReleaseTest = sampleReleaseTrains[0].copy(id = null)

        val response = client.post("$apiRoute/releaseTrains") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleReleaseTest))
            header("Authorization", "Bearer $testToken")
        }

        val responseReleaseTest: ReleaseTrain = Gson().fromJson(response.bodyAsText())

        assertEquals(6, responseReleaseTest.id)
        assertEquals(sampleReleaseTest.info, responseReleaseTest.info)
    }

    @Test
    fun testPutReleasetrains() = testApplication {


        val sampleReleaseTest = sampleReleaseTrains[0].copy(info = "coolInfo01")

        val response = client.put("$apiRoute/releaseTrains") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleReleaseTest))
            header("Authorization", "Bearer $testToken")
        }

        val responseReleaseTest: ReleaseTrain = Gson().fromJson(response.bodyAsText())

        assertEquals(1, responseReleaseTest.id)
        assertEquals(sampleReleaseTest.info, responseReleaseTest.info)

    }

    @Test
    fun testDeleteReleasetrainsId() = testApplication {

        client.delete("$apiRoute/releaseTrains/1") {
            header("Authorization", "Bearer $testToken")
        }

        val response = client.get("$apiRoute/releaseTrains") {
            header("Authorization", "Bearer $testToken")
        }

        val releaseTrains: List<ReleaseTrain> = Gson().fromJson(response.bodyAsText())

        assertEquals(4, releaseTrains.size)
    }

    @Test
    fun testGetReleasetrainsId() = testApplication {

        val response = client.get("$apiRoute/releaseTrains/1") {
            header("Authorization", "Bearer $testToken")
        }

        val responseReleaseTest: ReleaseTrain = Gson().fromJson(response.bodyAsText())

        assertEquals(1, responseReleaseTest.id)
    }
}