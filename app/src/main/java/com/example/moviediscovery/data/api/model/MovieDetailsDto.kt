package com.example.moviediscovery.data.api.model

import com.google.gson.annotations.SerializedName
import com.example.moviediscovery.domain.model.Genre
import com.example.moviediscovery.domain.model.MovieDetails

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("tagline") val tagline: String?
)

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

fun MovieDetailsDto.toDomainModel(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        runtime = runtime,
        genres = genres.map { it.toDomainModel() },
        popularity = popularity,
        tagline = tagline
    )
}

fun GenreDto.toDomainModel(): Genre {
    return Genre(
        id = id,
        name = name
    )
}