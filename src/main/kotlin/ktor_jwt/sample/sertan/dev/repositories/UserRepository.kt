package ktor_jwt.sample.sertan.dev.repositories

import java.util.UUID
import ktor_jwt.sample.sertan.dev.models.db.User

internal class UserRepository {

    private val users = mutableListOf<User>()

    fun findAll(): List<User> = users

    fun findById(id: UUID): User? = users.firstOrNull { it.id == id }

    fun findByUsername(username: String): User? = users.firstOrNull { it.username == username }

    fun save(user: User): Boolean = users.add(user)
}