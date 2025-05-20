package com.example.moviediscovery.util

import com.example.moviediscovery.util.Constants.BACKDROP_SIZE
import com.example.moviediscovery.util.Constants.IMAGE_BASE_URL
import com.example.moviediscovery.util.Constants.POSTER_SIZE

object ImageUtils {
    fun getPosterUrl(posterPath: String?): String? {
        return posterPath?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" }
    }

    fun getBackdropUrl(backdropPath: String?): String? {
        return backdropPath?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" }
    }
}