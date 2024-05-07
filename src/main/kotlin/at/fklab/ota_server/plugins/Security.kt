package at.fklab.ota_server.plugins


import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    authentication {
        basic("auth-basic") {
            validate { credentials ->

                if (credentials.name == "test" && credentials.password == "test") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}