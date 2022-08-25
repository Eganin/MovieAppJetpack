package com.eganin.jetpack.thebest.movieapp.domain.data.models.entity

import androidx.room.*
import com.eganin.jetpack.thebest.movieapp.domain.data.database.Contract
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem

@Entity(
    tableName = Contract.MovieDetails.TABLE_NAME,
    indices = [Index(Contract.MovieDetails.COLUMN_NAME_ID)]
)
data class MovieDetailsEntity(

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_TITLE)
    val title: String = "",

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_BACKDROP_PATH)
    val backdropPath: String = "",

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_GENRES_LIST)
    val genres: List<GenresItem> = emptyList(),

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_POPULARITY)
    val popularity: Double = 0.0,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_VOTE_COUNT)
    val voteCount: Int = 0,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_OVERVIEW)
    val overview: String = "",

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ORIGINAL_TITLE)
    val originalTitle: String = "",

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_RUNTIME)
    val runtime: Int = 0,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_POSTER_PATH)
    val posterPath: String = "",

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_VOTE_AVERAGE)
    val voteAverage: Double = 0.0,

    @ColumnInfo(name = Contract.MovieDetails.COLUMN_NAME_ADULT)
    val adult: Boolean = false,
)
