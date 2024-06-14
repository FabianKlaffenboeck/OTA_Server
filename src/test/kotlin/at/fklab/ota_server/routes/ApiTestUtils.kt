package at.fklab.ota_server.routes

import at.fklab.ota_server.module
import io.ktor.server.testing.*
import org.junit.Before
import kotlin.test.BeforeTest

abstract class ApiTestUtils {

    val apiVersion = "v0.0.1"
    val restRoute = "rest"
    val apiRoute = "/$restRoute/$apiVersion"
}