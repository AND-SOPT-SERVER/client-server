package org.sopt.hobby

import org.springframework.stereotype.Service
import java.util.*

@Service
class Service(
    private val userRegister: UserRegister,
    private val userRetriever: UserRetriever,
) {
    fun createUser(username: String, password: String, hobby: String): User {
        return try {
            val uuid = UUID.randomUUID()
            userRegister.saveUser(username, password, hobby, uuid.toString())
        } catch (e: Exception) {
            when (e) {
                is UserDupException -> {
                    throw DuplicatedException("00")
                }

                else -> throw e
            }
        }
    }

    fun login(username: String, password: String): String {
        try {
            val user = userRetriever.findUserByUsername(username)
            if (user.password != password) {
                throw ForbiddenException("01")
            }

            return user.token
        } catch (e: Exception) {
            when (e) {
                is UserNotFoundException -> {
                    throw BadRequestException("02")
                }

                else -> throw e
            }
        }
    }

    fun getHobby(token: String): User {
        return try {
            userRetriever.findUserByToken(token)
        } catch (e: Exception) {
            when (e) {
                is InvalidTokenException -> {
                    throw ForbiddenException("00")
                }

                else -> throw e
            }
        }
    }

    fun getHobby(no: Long): User {
        return try {
            userRetriever.findUser(no)
        } catch (e: Exception) {
            when (e) {
                is UserNotFoundException -> {
                    throw NotFoundException("01")
                }

                else -> throw e
            }
        }
    }

    fun patchHobby(token: String, password: String, hobby: String) {
        try {
            userRegister.putUser(token, password, hobby)
        } catch (e: Exception) {
            when (e) {
                is InvalidTokenException -> {
                    throw ForbiddenException("00")
                }

                else -> throw e
            }
        }
    }
}