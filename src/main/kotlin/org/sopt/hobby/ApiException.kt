package org.sopt.hobby

abstract class ApiClientException(val code: String) : RuntimeException()

class BadRequestException(code: String): ApiClientException(code)
class NotFoundException(code: String) : ApiClientException(code)
class DuplicatedException(code: String) : ApiClientException(code)
class ForbiddenException(code: String) : ApiClientException(code)