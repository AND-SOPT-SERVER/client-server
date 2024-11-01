package org.sopt.hobby

import jakarta.persistence.*

@Table(
    name = "user_table",
    uniqueConstraints = [
        UniqueConstraint(name = "uk1", columnNames = ["username"]),
        UniqueConstraint(name = "uk2", columnNames = ["token"])
    ]
)
@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var no: Long? = null,

    @Column(nullable = false, length = 8, name = "username")
    private var username: String,

    @Column(nullable = false, length = 8, name = "password")
    private var password: String,

    @Column(nullable = false, length = 8, name = "hobby")
    private var hobby: String,

    @Column(nullable = false)
    private var token: String,
) {
    fun getNo(): Long = no ?: throw NullPointerException()
    fun getUsername() = username
    fun getPassword() = password
    fun getHobby() = hobby
    fun getToken() = token

    fun update(password: String?, hobby: String?) {
        this.password = password ?: this.password
        this.hobby = hobby ?: this.hobby
    }
}