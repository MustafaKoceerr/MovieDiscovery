package com.example.moviediscovery.data.util

import com.google.gson.Gson

fun createNetworkErrorMessage(errorCode: Int, errorBody: String?): String {
    return when (errorCode) {
        401 -> "Authentication error: Please log in again."
        403 -> "You don't have permission to access this resource."
        404 -> "The requested resource was not found."
        429 -> "Too many requests. Please try again later."
        500, 502 -> "Server error. Please try again later."
        503 -> "Service unavailable. Please try again later."
        else -> {
            errorBody?.let { body ->
                try {
                    val errorResponse = Gson().fromJson(body, ErrorResponseDto::class.java)
                    errorResponse.message ?: "Unknown error occurred (Code: $errorCode)"
                } catch (e: Exception) {
                    "Error parsing response (Code: $errorCode)"
                }
            } ?: "Unknown error occurred (Code: $errorCode)"
        }
    }
}

data class ErrorResponseDto(
    val message: String? = null,
    val status: String? = null,
    val timestamp: String? = null
)