package com.eganin.jetpack.thebest.movieapp.domain.data.utils

import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse

/*
Movie - класс для списка в приложении
MovieEntity - класс для сохранения данных о фильме в БД
MovieDetailsResponse- класс для детальных данных в DetailPage
MovieDetailsEntity- класс для сохранения детальной информации о фильмев БД
 */
fun Movie.toMovieEntity(genres: List<GenresItem>): MovieEntity {
    return MovieEntity(
        id = this.id,
        originalTitle = this.originalTitle,
        title = this.title,
        genreIds = this.genreIds,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        voteAverage = this.voteAverage,
        adult = this.adult,
        voteCount = this.voteCount,
        genres = genres,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        originalTitle = this.originalTitle,
        title = this.title,
        genreIds = this.genreIds,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        voteCount = this.voteCount,
        voteAverage = this.voteAverage,
        adult = this.adult,
    )
}

fun MovieDetailsResponse.toMovieDetailsEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        id = this.id,
        title = this.title,
        backdropPath = this.backdropPath,
        genres = this.genres,
        popularity = this.popularity,
        voteCount = this.voteCount,
        overview = this.overview,
        originalTitle = this.originalTitle,
        runtime = this.runtime,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        adult = this.adult,
    )
}

fun MovieDetailsEntity.toMovieDetailsResponse(): MovieDetailsResponse {
    return MovieDetailsResponse(
        title,
        backdropPath,
        genres,
        popularity,
        id,
        voteCount,
        overview,
        originalTitle,
        runtime,
        posterPath,
        voteAverage,
        adult,
    )
}