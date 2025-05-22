package com.example.moviediscovery.data.api.model

import com.example.moviediscovery.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("popularity") val popularity: Double?
)

fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title?.takeIf { it.isNotBlank() } ?: "Unknown Title",
        overview = overview?.takeIf { it.isNotBlank() } ?: "No overview available",
        posterPath = posterPath, // Keep null as is for image loading logic
        backdropPath = backdropPath, // Keep null as is for image loading logic
        releaseDate = releaseDate?.takeIf { it.isNotBlank() } ?: "Unknown",
        voteAverage = voteAverage?.takeIf { it >= 0 } ?: 0.0,
        genreIds = genreIds ?: emptyList(),
        popularity = popularity?.takeIf { it >= 0 } ?: 0.0
    )
}