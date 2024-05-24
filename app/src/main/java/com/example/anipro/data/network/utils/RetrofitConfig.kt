package com.example.anipro.data.network.utils

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException

inline fun <Api : Any, Data : Any> Response<Api>.handleRequest(mapper: (Api) -> Data): NetworkResult<Data> {
    return try {
        return if (isSuccessful) {
            body()?.let {
                NetworkResult.Success(mapper.invoke(it))
            } ?: NetworkResult.Error(
                ApiError(
                    ApiErrors.RESPONSE_NULL,
                    null,
                    "Response body is null"
                )
            )
        } else {
            var subCode: String? = null
            var message: String? = null
            try {
                val error =
                    Gson().fromJson(
                        this.errorBody()!!.charStream(),
                        ApiResponse::class.java
                    )
                subCode = error.error
                message = error.messages
            } catch (_: Exception) {
            }
            NetworkResult.Error(ApiError(code(), subCode, message ?: errorBody()?.string()))
        }
    } catch (exception: IOException) {
        NetworkResult.Error(ApiError(ApiErrors.GENERIC, null, exception.toString()))
    }
}

sealed interface NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>
    data class Error(val apiError: ApiError) : NetworkResult<Nothing>
}

inline fun <T : Any> NetworkResult<T>.handleNetworkResult(onSuccess: (T) -> Unit) {
    when (this) {
        is NetworkResult.Success -> onSuccess(data)

//        is NetworkResult.Error -> throw RuntimeException("There was an error with code = $code, " +
//                "subcode  = $subCode and message = $message")
        is NetworkResult.Error -> throw CustomApiException(apiError)
    }
}

class CustomApiException(apiError: ApiError) : Exception(
    "There was an error with code = ${apiError.code}, " +
            "sub code  = ${apiError.subCode} and message = ${apiError.message}"
)

data class ApiError(
    val code: Int? = null,
    val subCode: String? = null,
    val message: String? = null
)
