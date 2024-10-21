package org.sopt.hobby

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?

    fun findByToken(token: String): UserEntity?
}