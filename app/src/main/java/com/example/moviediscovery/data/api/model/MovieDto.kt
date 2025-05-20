package com.example.moviediscovery.data.api.model

import android.icu.text.CaseMap.Title
import com.example.moviediscovery.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("popularity") val popularity: Double
)

fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        popularity = popularity
    )
}