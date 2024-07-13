package ktor_jwt.sample.sertan.dev.models.response

import java.util.UUID
import kotlinx.serialization.Serializable
import ktor_jwt.sample.sertan.dev.helpers.UUIDSerializer

@Serializable
internal class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String
)