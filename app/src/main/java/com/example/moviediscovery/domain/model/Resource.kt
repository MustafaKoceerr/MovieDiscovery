package com.example.moviediscovery.domain.model

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val code: Int = 0) : Resource<Nothing>()
}