package com.eganin.jetpack.thebest.movieapp.ui.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.Movie
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.MovieDetailsResponse

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Movie.toMovieEntity(genres: List<GenresItem>?): MovieEntity {
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