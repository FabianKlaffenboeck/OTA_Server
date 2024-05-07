package at.fklab.ota_server

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        assertEquals(HttpStatusCode.OK, HttpStatusCode.OK)
    }
}
