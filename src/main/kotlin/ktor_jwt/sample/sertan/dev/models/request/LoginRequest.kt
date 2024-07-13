package ktor_jwt.sample.sertan.dev.models.request

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequest(
    val username: String,
    val password: String
)