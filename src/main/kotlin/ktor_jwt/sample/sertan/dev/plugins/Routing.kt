package ktor_jwt.sample.sertan.dev.plugins

import io.ktor.server.application.Application
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import ktor_jwt.sample.sertan.dev.routing.authRoute
import ktor_jwt.sample.sertan.dev.routing.userRoute
import ktor_jwt.sample.sertan.dev.services.JwtService
import ktor_jwt.sample.sertan.dev.services.UserService

internal fun Application.configureRouting(jwtService: JwtService, userService: UserService) {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        route("/api/auth") { authRoute(jwtService) }
        route("/api/user") { userRoute(userService) }
    }
}
