package ktor_jwt.sample.sertan.dev.mappers

import java.util.UUID
import ktor_jwt.sample.sertan.dev.models.db.User
import ktor_jwt.sample.sertan.dev.models.request.UserRequest
import ktor_jwt.sample.sertan.dev.models.response.UserResponse

internal fun UserRequest.toModel(): User = User(
    id = UUID.randomUUID(),
    username = this.username,
    password = this.password
)

internal fun User.toResponse(): UserResponse = UserResponse(
    id = this.id,
    username = this.username
)
