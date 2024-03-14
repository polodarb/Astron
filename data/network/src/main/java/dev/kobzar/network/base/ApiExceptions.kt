package dev.kobzar.network.base

sealed class ApiExceptions(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {
    class BadRequestException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class NotFoundException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class MethodNotAllowedException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class UnauthorizedException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class ForbiddenException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class UnknownApiException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)

    class NoNetworkException(message: String? = null, cause: Throwable? = null) :
        ApiExceptions(message, cause)
}
