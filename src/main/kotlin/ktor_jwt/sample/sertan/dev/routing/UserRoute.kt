package ktor_jwt.sample.sertan.dev.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import ktor_jwt.sample.sertan.dev.mappers.toModel
import ktor_jwt.sample.sertan.dev.mappers.toResponse
import ktor_jwt.sample.sertan.dev.models.db.User
import ktor_jwt.sample.sertan.dev.models.request.UserRequest
import ktor_jwt.sample.sertan.dev.services.UserService

internal fun Route.userRoute(userService: UserService) {

    post {
        val userRequest = call.receive<UserRequest>()
        val createdUser = userService.save(user = userRequest.toModel())
            ?: return@post call.respond(HttpStatusCode.Conflict)

        call.response.header(
            name = "id",
            value = createdUser.id.toString()
        )

        call.respond(message = HttpStatusCode.Created)
    }

    authenticate {
        get {
            val users = userService.findAll().map(User::toResponse)
            call.respond(message = users, status = HttpStatusCode.OK)
        }
    }

    authenticate {
        get("/{id}") {
            val id: String = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = userService.findById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(message = user.toResponse(), status = HttpStatusCode.OK)
        }
    }
}