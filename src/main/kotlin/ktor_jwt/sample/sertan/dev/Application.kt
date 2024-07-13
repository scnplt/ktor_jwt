package ktor_jwt.sample.sertan.dev

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import ktor_jwt.sample.sertan.dev.plugins.configureRouting
import ktor_jwt.sample.sertan.dev.plugins.configureSecurity
import ktor_jwt.sample.sertan.dev.plugins.configureSerialization
import ktor_jwt.sample.sertan.dev.repositories.UserRepository
import ktor_jwt.sample.sertan.dev.services.JwtService
import ktor_jwt.sample.sertan.dev.services.UserService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val userRepository = UserRepository()
    val userService = UserService(userRepository)
    val jwtService = JwtService(this, userService)

    install(CallLogging) {
        format { call ->
            val method = call.request.httpMethod.value
            val path = call.request.path()
            val statusCode = call.response.status()
            "$method, $path, $statusCode"
        }
    }

    configureSerialization()
    configureSecurity(jwtService)
    configureRouting(jwtService, userService)
}
