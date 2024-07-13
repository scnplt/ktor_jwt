package ktor_jwt.sample.sertan.dev.models.db

import java.util.UUID

internal data class User(
    val id: UUID,
    val username: String,
    val password: String
)