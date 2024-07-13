package ktor_jwt.sample.sertan.dev.services

import java.util.UUID
import ktor_jwt.sample.sertan.dev.models.db.User
import ktor_jwt.sample.sertan.dev.repositories.UserRepository

internal class UserService(private val userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: String): User? = userRepository.findById(id = UUID.fromString(id))

    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

    fun save(user: User): User? {
        val isExist = userRepository.findByUsername(username = user.username) != null
        if(isExist) return null
        return if (userRepository.save(user)) user else null
    }
}