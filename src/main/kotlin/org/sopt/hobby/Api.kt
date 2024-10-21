package org.sopt.hobby

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Api(
    private val service: Service
) {
    @PostMapping("/user")
    fun postUser(
        @Valid @RequestBody request: UserCreateRequest,
    ): ResponseEntity<Response<UserCreateResponse>> {
        val userCreated = service.createUser(request.username, request.password, request.hobby)

        return ResponseEntity.ok(
            Response(result = UserCreateResponse(userCreated.no))
        )
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
    ): ResponseEntity<Response<LoginResponse>> {
        val token: String = service.login(request.username, request.password)

        return ResponseEntity.ok(
            Response(result = LoginResponse(token))
        )
    }

    @GetMapping("/user/my-hobby")
    fun getMyHobby(
        @RequestHeader("token") token: String,
    ): ResponseEntity<Response<HobbyResponse>> {
        val user = service.getHobby(token)

        return ResponseEntity.ok(
            Response(result = HobbyResponse(user.hobby))
        )
    }

    @GetMapping("/user/{no}/hobby")
    fun getHobby(
        @PathVariable("no") no: Long,
    ): ResponseEntity<Response<HobbyResponse>> {
        val user = service.getHobby(no)

        return ResponseEntity.ok(
            Response(result = HobbyResponse(user.hobby))
        )
    }

    @PutMapping("/user")
    fun putUser(
        @RequestHeader("token") token: String,
        @Valid @RequestBody request: PutUserRequest,
    ): ResponseEntity<Response<Unit>> {
        service.patchHobby(token, request.password, request.hobby)

        return ResponseEntity.ok().build()
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val result: T? = null,
    val code: String? = null,
)

data class UserCreateRequest(
    @field:NotBlank
    @field:Size(max = 8)
    val username: String,

    @field:NotBlank
    @field:Size(max = 8)
    val password: String,

    @field:NotBlank
    @field:Size(max = 8)
    val hobby: String,
)

data class LoginRequest(
    @field:NotBlank
    @field:Size(max = 8)
    val username: String,

    @field:NotBlank
    @field:Size(max = 8)
    val password: String,
)

data class PutUserRequest(
    @field:NotBlank
    @field:Size(max = 8)
    val password: String,

    @field:NotBlank
    @field:Size(max = 8)
    val hobby: String,
)

data class UserCreateResponse(
    val no: Long,
)

data class LoginResponse(
    val token: String,
)

data class HobbyResponse(
    val hobby: String,
)