package com.eganin.jetpack.thebest.movieapp.domain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites WHERE id_movie == :id")
    fun getFavouriteMovieUsingID(id: Int): FavouriteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteMovie(favouriteMovie: FavouriteEntity)

    @Query("DELETE FROM favourites")
    fun deleteAllFavouriteMovie()

    @Query("DELETE FROM favourites WHERE id_movie == :id")
    fun deleteFavouriteMovieUsingID(id: Int)
}