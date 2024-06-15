package at.fklab.ota_server.routes

import at.fklab.ota_server.plugins.configureDatabases
import io.ktor.server.testing.*
import org.junit.Before

abstract class ApiTestUtils {

    val apiVersion = "v0.0.1"
    val restRoute = "rest"
    val apiRoute = "/$restRoute/$apiVersion"


    @Before
    fun resetDB() = testApplication {
        application {
            configureDatabases("jdbc:sqlite:TestDB", "root", "", true, true, true)
        }
    }
}