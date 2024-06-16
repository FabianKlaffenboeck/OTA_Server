package at.fklab.ota_server.routes

import at.fklab.ota_server.development.sampleDevices
import at.fklab.ota_server.models.Device
import at.fklab.ota_server.module
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class DeviceRouteTest : ApiTestUtils() {


    @Test
    fun testGetDevices() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/devices")

        val devices: List<Device> = Gson().fromJson(response.bodyAsText())
        assertEquals(sampleDevices.size, devices.size)
    }

    @Test
    fun testPostDevices() = testApplication {
        application {
            module()
        }

        val sampleDevice = sampleDevices[0].copy(id = null, info = "coolInfo01")

        val response = client.post("$apiRoute/devices") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleDevice))
        }

        val responseDevice: Device = Gson().fromJson(response.bodyAsText())

        assertEquals(sampleDevices.size + 1, responseDevice.id)
        assertEquals(sampleDevice.info, responseDevice.info)

    }

    @Test
    fun testPutDevices() = testApplication {
        application {
            module()
        }

        val sampleDevice = sampleDevices[0].copy(info = "coolInfo01")

        val response = client.put("$apiRoute/devices") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleDevice))
        }

        val responseDevice: Device = Gson().fromJson(response.bodyAsText())

        assertEquals(1, responseDevice.id)
        assertEquals(sampleDevice.info, responseDevice.info)

    }

    @Test
    fun testDeleteDevicesId() = testApplication {
        application {
            module()
        }
        client.delete("$apiRoute/devices/1")

        val response = client.get("$apiRoute/devices")
        val devices: List<Device> = Gson().fromJson(response.bodyAsText())

        assertEquals(sampleDevices.size - 1, devices.size)

    }

    @Test
    fun testGetDevicesId() = testApplication {
        application {
            module()
        }
        val response = client.get("$apiRoute/devices/1")

        val device: Device = Gson().fromJson(response.bodyAsText())

        assertEquals(1, device.id)
    }
}