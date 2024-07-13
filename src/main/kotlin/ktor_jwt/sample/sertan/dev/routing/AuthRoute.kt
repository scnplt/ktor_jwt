package ktor_jwt.sample.sertan.dev.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import ktor_jwt.sample.sertan.dev.models.request.LoginRequest
import ktor_jwt.sample.sertan.dev.services.JwtService

internal fun Route.authRoute(jwtService: JwtService) {

    post {
        val loginRequest = call.receive<LoginRequest>()

        val token: String = jwtService.createJWTToken(loginRequest)
            ?: return@post call.respond(HttpStatusCode.Unauthorized)

        call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
    }
}