package at.fklab.ota_server.routes

import at.fklab.ota_server.development.sampleFirmwareReleases
import at.fklab.ota_server.models.FirmwareRelease
import at.fklab.ota_server.module
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class FirmwareReleasesRouteTest : ApiTestUtils() {

    @Test
    fun testGetFirmwarereleases() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/firmwareReleases")

        val firmwareRelease: List<FirmwareRelease> = Gson().fromJson(response.bodyAsText())

        assertEquals(sampleFirmwareReleases.size, firmwareRelease.size)
    }

    @Test
    fun testPostFirmwarereleases() = testApplication {
        application {
            module()
        }

        val sampleFirmwareRelease = sampleFirmwareReleases[0].copy(id = null, info = "coolInfo01")

        val response = client.post("$apiRoute/firmwareReleases") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleFirmwareRelease))
        }

        val responseFirmwareRelease: FirmwareRelease = Gson().fromJson(response.bodyAsText())

        assertEquals(3, responseFirmwareRelease.id)
        assertEquals(sampleFirmwareRelease.info, responseFirmwareRelease.info)
    }

    @Test
    fun testPutFirmwarereleases() = testApplication {
        application {
            module()
        }

        val sampleFirmwareRelease = sampleFirmwareReleases[0].copy(info = "coolInfo01")

        val response = client.put("$apiRoute/firmwareReleases") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleFirmwareRelease))
        }

        val responseFirmwareRelease: FirmwareRelease = Gson().fromJson(response.bodyAsText())

        assertEquals(1, responseFirmwareRelease.id)
        assertEquals(sampleFirmwareRelease.info, responseFirmwareRelease.info)
    }

    @Test
    fun testDeleteFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        client.delete("$apiRoute/firmwareReleases/1")

        val response = client.get("$apiRoute/firmwareReleases")

        val firmwareRelease: List<FirmwareRelease> = Gson().fromJson(response.bodyAsText())

        assertEquals(sampleFirmwareReleases.size - 1, firmwareRelease.size)
    }

    @Test
    fun testGetFirmwarereleasesId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/firmwareReleases/1")

        val firmwareRelease: FirmwareRelease = Gson().fromJson(response.bodyAsText())

        assertEquals(1, firmwareRelease.id)
    }

    //    @Test
//    fun testGetFirmwarereleasesIdBinary() = testApplication {
//        application {
//            module()
//        }
//        client.get("$apiRoute/firmwareReleases/1/binary")
//    }
//
    @Test
    fun testPostFirmwarereleasesIdBinary() = testApplication {
        application {
            module()
        }


        val response = client.post("$apiRoute/firmwareReleases/1/binary") {
            val fileBytes = javaClass.classLoader.getResource("firmware-latest.hex")!!.readBytes()
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file", fileBytes, Headers.build {
                            append(HttpHeaders.ContentType, "text/plain")
                            append(HttpHeaders.ContentDisposition, "filename=\"firmware-latest.hex\"")
                        })
                    },
                )
            )
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testPostFirmwarereleasesIdBinaryEmtpty() = testApplication {
        application {
            module()
        }


        val response = client.post("$apiRoute/firmwareReleases/1/binary") {
            setBody(MultiPartFormDataContent(formData { }))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}