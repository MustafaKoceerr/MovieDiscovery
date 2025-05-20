package com.example.moviediscovery.data.util

import com.example.moviediscovery.domain.model.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> Response<T>,
    crossinline errorMapper: (errorBody: String?, errorCode: Int) -> String = { errorBody, errorCode ->
        errorBody?.let {
            try {
                val errorResponse = Gson().fromJson(it, ErrorResponseDto::class.java)
                errorResponse.message ?: "Unknown error occurred (Code: $errorCode)"
            } catch (e: Exception) {
                "Error parsing response (Code: $errorCode)"
            }
        } ?: "Unknown error occurred (Code: $errorCode)"
    }
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    val response = apiCall()
    if (response.isSuccessful) {
        response.body()?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Error("Response body is null"))
    } else {
        val errorBody = response.errorBody()?.string()
        val errorMessage = errorMapper(errorBody, response.code())
        emit(Resource.Error(errorMessage, response.code()))
    }
}.catch { e ->
    when (e) {
        is IOException -> {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }

        else -> {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}.flowOn(Dispatchers.IO)

data class ErrorResponseDto(
    val message: String? = null,
    val status: String? = null,
    val timestamp: String? = null
)