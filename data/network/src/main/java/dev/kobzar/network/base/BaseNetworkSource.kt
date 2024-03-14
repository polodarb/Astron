package dev.kobzar.network.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

interface BaseNetworkSource {

    suspend fun <T> apiCall(call: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            try {
                call()
            } catch (ex: Exception) {
                Log.e("BaseNetworkSource", ex.stackTraceToString(), ex)
                when (ex) {
                    is HttpException -> throw handleHttpException(ex)
                    is UnknownHostException -> throw ApiExceptions.NoNetworkException(
                        "No internet connection or unknown host",
                        ex
                    )

                    else -> throw ApiExceptions.UnknownApiException(ex.message, ex)
                }
            }
        }
    }

    private fun handleHttpException(httpException: HttpException): ApiExceptions {
        return when (httpException.code()) {
            400 -> ApiExceptions.BadRequestException("Bad request exception", httpException)
            401 -> ApiExceptions.UnauthorizedException("Unauthorized exception", httpException)
            403 -> ApiExceptions.ForbiddenException("Forbidden exception", httpException)
            404 -> ApiExceptions.NotFoundException("Host not found", httpException)
            405 -> ApiExceptions.MethodNotAllowedException("Method not allowed", httpException)
            else -> ApiExceptions.UnknownApiException(httpException.message(), httpException)
        }
    }
}
