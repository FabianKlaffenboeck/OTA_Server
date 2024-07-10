package at.fklab.ota_server.routes

import at.fklab.ota_server.development.sampleUsers
import at.fklab.ota_server.infra.configureDatabases
import at.fklab.ota_server.models.TokenDTO
import com.google.gson.Gson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

abstract class ApiTestUtils {

    val apiVersion = "v0.0.1"
    val restRoute = "rest"
    val apiRoute = "/$restRoute/$apiVersion"

    var testToken = ""

    @Before
    fun resetDB() = testApplication {
        application {
            configureDatabases("jdbc:sqlite:TestDB", "root", "", true, true, true)
            getToken()
        }
    }

    private fun getToken() = testApplication {
        val tokenData = client.post("$apiRoute/login") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(sampleUsers[2]))
        }
        testToken = Gson().fromJson<TokenDTO>(tokenData.bodyAsText()).token
    }


    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, typeOf<T>().javaType)
}