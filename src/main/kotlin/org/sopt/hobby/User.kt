package org.sopt.hobby

data class User(
    val no: Long,
    val username: String,
    val password: String,
    val hobby: String,
    val token: String,
)