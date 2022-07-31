package com.eganin.jetpack.thebest.movieapp.domain.data.database

import android.provider.BaseColumns

object Contract {
    const val DATABASE_NAME = "movie.db"

    object Movies{
        const val TABLE_NAME ="movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_ORIGINAL_TITLE = "original_title"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_GENRE_IDS = "genre_ids"
        const val COLUMN_NAME_POSTER_PATH = "poster_path"
        const val COLUMN_NAME_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_NAME_VOTE_AVERAGE = "vote_average"
        const val COLUMN_NAME_ADULT = "adult"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"
        const val COLUMN_NAME_GENRES_LIST = "genres"
    }

    object MovieDetails{
        const val TABLE_NAME="movie_details"

        const val COLUMN_NAME_ID =BaseColumns._ID
        const val COLUMN_NAME_POPULARITY ="popularity"
        const val COLUMN_NAME_OVERVIEW ="overview"
        const val COLUMN_NAME_RUNTIME ="runtime"
        const val COLUMN_NAME_ID_GENRE ="id_genre"
        const val COLUMN_NAME_NAME_GENRE ="name"
        const val COLUMN_NAME_TITLE ="title"
        const val COLUMN_NAME_BACKDROP_PATH ="backdrop_path"
        const val COLUMN_NAME_GENRES_LIST ="genres"
        const val COLUMN_NAME_VOTE_COUNT ="vote_count"
        const val COLUMN_NAME_ORIGINAL_TITLE = "original_title"
        const val COLUMN_NAME_POSTER_PATH = "poster_path"
        const val COLUMN_NAME_VOTE_AVERAGE = "vote_average"
        const val COLUMN_NAME_ADULT = "adult"
    }

    object Credits{
        const val TABLE_NAME = "credits"

        const val COLUMN_NAME_ID =BaseColumns._ID
        const val COLUMN_PROFILE_PATH ="profile_path"
        const val COLUMN_NAME ="name"
        const val COLUMN_ORIGINAL_NAME ="original_name"
        const val COLUMN_CHARACTER ="character"
        const val COLUMN_ID_BY_MOVIE ="id_movie"
    }
}