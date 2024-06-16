package at.fklab.ota_server.routes

import at.fklab.ota_server.plugins.configureDatabases
import com.google.gson.Gson
import io.ktor.server.testing.*
import org.junit.Before
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

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

    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, typeOf<T>().javaType)
}