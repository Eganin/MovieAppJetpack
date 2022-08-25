package com.eganin.jetpack.thebest.movieapp.domain.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem

@Dao
interface CreditsDao {

    @Query("SELECT * FROM credits WHERE id_movie == :id ")
    fun getAllCredits(id: Int) : List<CastItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCredits(credits: List<CastItem>)

    @Query("DELETE FROM credits")
    fun deleteAllCredits()

    @Query("DELETE FROM credits WHERE _id == :id")
    fun deleteCreditsById(id: Int)
}