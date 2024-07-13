package ktor_jwt.sample.sertan.dev.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import ktor_jwt.sample.sertan.dev.services.JwtService

internal fun Application.configureSecurity(jwtService: JwtService) {
    install(Authentication) {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate { credential -> jwtService.customValidator(credential) }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
