package org.sopt.hobby

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(ApiClientException::class)
    protected fun handlerApiClientException(e: ApiClientException): ResponseEntity<Response<Unit>> {
        return when (e) {
            is BadRequestException -> ResponseEntity.status(400).body(Response(code = e.code))
            is NotFoundException -> ResponseEntity.status(404).body(Response(code = e.code))
            is DuplicatedException -> ResponseEntity.status(409).body(Response(code = e.code))
            is ForbiddenException -> ResponseEntity.status(403).body(Response(code = e.code))
            else -> ResponseEntity.status(400).body(Response(code = "999"))
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleMethodArgumentNotValidException(e: HttpMessageNotReadableException): ResponseEntity<Response<Unit>> {
        return ResponseEntity.status(400).body(Response(code = "00"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Response<Unit>> {
        return ResponseEntity.status(400).body(Response(code = "01"))
    }

    @ExceptionHandler(MissingRequestHeaderException::class)
    protected fun handleMissingRequestHeaderException(e: MissingRequestHeaderException): ResponseEntity<Response<Unit>> {
        return ResponseEntity.status(401).body(Response(code = "00"))
    }
}