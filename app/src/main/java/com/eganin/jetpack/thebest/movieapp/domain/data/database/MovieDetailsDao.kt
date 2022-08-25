package com.eganin.jetpack.thebest.movieapp.domain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM movie_details WHERE _id == :id LIMIT 1")
    suspend fun getAllInfo(id: Int) : MovieDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movie: MovieDetailsEntity)

    @Query("DELETE FROM movie_details")
    suspend fun deleteAllInfoMovie()

    @Query("DELETE FROM movie_details WHERE _id == :id")
    fun deleteInfoMovieById(id: Int)
}