package com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.converter

import androidx.room.TypeConverter
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.GenresItem
import java.lang.Exception

class Converters {
    @TypeConverter
    fun genreIdsToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToGenreIds(str: String): List<Int> {
        return str.split(",").map {
            try {
                it.toInt()
            }catch (e : Exception){
                0
            }
        }
    }

    @TypeConverter
    fun  genreListToString(genres: List<GenresItem>): String {
        return genres.joinToString(separator = ",") { it.id.toString() + ":" + it.name }
    }

    @TypeConverter
    fun stringToGenreList(str: String): List<GenresItem> {
        val lst = str.split(",")
        val listGenres = mutableListOf<GenresItem>()
        for (genre in lst) {
            val pair = genre.split(":")
            val result = GenresItem(id = pair[0].toInt(), name = pair[1])
            listGenres.add(result)
        }
        return listGenres
    }

}