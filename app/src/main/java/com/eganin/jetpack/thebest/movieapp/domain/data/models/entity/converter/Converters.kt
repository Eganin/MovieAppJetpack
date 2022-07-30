package com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.converter

import androidx.room.TypeConverter
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entities.GenresItemDetails

class Converters {
    @TypeConverter
    fun genreIdsToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToGenreIds(str: String): List<Int> {
        return str.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun genreListToString(genres: List<GenresItemDetails>): String {
        return genres.joinToString(separator = ",") { it.id.toString() + ":" + it.name }
    }

    @TypeConverter
    fun stringToGenreList(str: String): List<GenresItemDetails> {
        val lst = str.split(",")
        val listGenres = mutableListOf<GenresItemDetails>()
        for (genre in lst!!) {
            val pair = genre.split(":")
            val result = GenresItemDetails(id = pair[0].toInt(), name = pair[1])
            listGenres.add(result)
        }
        return listGenres
    }

}