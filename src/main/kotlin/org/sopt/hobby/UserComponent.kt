package org.sopt.hobby

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class UserRetriever(
    private val userRepository: UserRepository,
) {
    fun findUserByUsername(username: String): User {
        val userEntity = userRepository.findByUsername(username)
            ?: throw UserNotFoundException()

        return User(
            no = userEntity.getNo(),
            username = userEntity.getUsername(),
            password = userEntity.getPassword(),
            hobby = userEntity.getHobby(),
            token = userEntity.getToken(),
        )
    }

    fun findUserByToken(token: String): User {
        val userEntity = userRepository.findByToken(token)
            ?: throw InvalidTokenException()

        return User(
            no = userEntity.getNo(),
            username = userEntity.getUsername(),
            password = userEntity.getPassword(),
            hobby = userEntity.getHobby(),
            token = userEntity.getToken(),
        )
    }

    fun findUser(no: Long): User {
        val userEntity = userRepository.findById(no).orElse(null)
            ?: throw UserNotFoundException()

        return User(
            no = userEntity.getNo(),
            username = userEntity.getUsername(),
            password = userEntity.getPassword(),
            hobby = userEntity.getHobby(),
            token = userEntity.getToken(),
        )
    }
}

@Component
class UserRegister(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun saveUser(username: String, password: String, hobby: String, uuid: String): User {
        val userEntity = try {
            userRepository.save(
                UserEntity(username = username, password = password, hobby = hobby, token = uuid)
            )
        } catch (e: Exception) {
            throw UserDupException()
        }

        return User(
            no = userEntity.getNo(),
            username = userEntity.getUsername(),
            password = userEntity.getPassword(),
            hobby = userEntity.getHobby(),
            token = userEntity.getToken(),
        )
    }

    @Transactional
    fun putUser(token: String, password: String?, hobby: String?) {
        val userEntity = userRepository.findByToken(token)
            ?: throw InvalidTokenException()

        userEntity.update(password, hobby)
    }
}

class UserNotFoundException : RuntimeException()
class UserDupException : RuntimeException()
class InvalidTokenException : RuntimeException()