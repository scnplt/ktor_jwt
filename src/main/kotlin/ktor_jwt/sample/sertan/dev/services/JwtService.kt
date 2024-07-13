package ktor_jwt.sample.sertan.dev.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.Date
import ktor_jwt.sample.sertan.dev.models.db.User
import ktor_jwt.sample.sertan.dev.models.request.LoginRequest

internal class JwtService(
    private val application: Application,
    private val userService: UserService
) {

    private val secret: String = getConfigProperty("jwt.secret")
    private val issuer: String = getConfigProperty("jwt.issuer")
    private val audience: String = getConfigProperty("jwt.audience")

    val realm: String = getConfigProperty("jwt.realm")

    val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    private fun getConfigProperty(path: String): String =
        application.environment.config.property(path).getString()

    fun createJWTToken(loginRequest: LoginRequest): String? {
        val user: User = userService.findByUsername(username = loginRequest.username) ?: return null
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("username", loginRequest.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
            .sign(Algorithm.HMAC256(secret))
    }

    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val username: String = credential.payload.getClaim("username").asString() ?: return null
        userService.findByUsername(username) ?: return null
        return if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
    }
}