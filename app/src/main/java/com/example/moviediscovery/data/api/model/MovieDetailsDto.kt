package com.example.moviediscovery.data.api.model

import com.google.gson.annotations.SerializedName
import com.example.moviediscovery.domain.model.Genre
import com.example.moviediscovery.domain.model.MovieDetails

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("tagline") val tagline: String?
)

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?
)

fun MovieDetailsDto.toDomainModel(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title?.takeIf { it.isNotBlank() } ?: "Unknown Title",
        overview = overview?.takeIf { it.isNotBlank() } ?: "No overview available",
        posterPath = posterPath, // Keep null as is for image loading logic
        backdropPath = backdropPath, // Keep null as is for image loading logic
        releaseDate = releaseDate?.takeIf { it.isNotBlank() } ?: "Unknown",
        voteAverage = voteAverage?.takeIf { it >= 0 } ?: 0.0,
        runtime = runtime?.takeIf { it > 0 }, // Keep null if runtime is 0 or negative
        genres = genres?.mapNotNull { it.toDomainModel() } ?: emptyList(),
        popularity = popularity?.takeIf { it >= 0 } ?: 0.0,
        tagline = tagline?.takeIf { it.isNotBlank() } // Keep null if empty or blank
    )
}

fun GenreDto.toDomainModel(): Genre? {
    return if (name?.isNotBlank() == true) {
        Genre(
            id = id,
            name = name
        )
    } else {
        null // Filter out genres with invalid names
    }
}