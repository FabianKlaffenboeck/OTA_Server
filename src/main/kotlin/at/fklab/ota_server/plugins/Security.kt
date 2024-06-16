package at.fklab.ota_server.plugins


import io.ktor.server.application.*
import io.ktor.server.auth.*

const val DEVICE_AUTH = "DEVICE_AUTH"

fun Application.configureSecurity() {
    authentication {
        basic(DEVICE_AUTH) {
            validate { credentials ->
                validateToken(credentials.name, credentials.password)
            }
        }
    }
}

fun validateToken(name: String, password: String): UserIdPrincipal? {
    if (name.isEmpty() || password.isEmpty()) {
        return null
    }
    return UserIdPrincipal(name)
}