package com.eganin.jetpack.thebest.movieapp.domain.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eganin.jetpack.thebest.movieapp.domain.data.database.Contract

@Entity(
    tableName = Contract.Favourite.TABLE_NAME,
    indices = [Index(
        Contract.Favourite.COLUMN_NAME_ID,
        unique = true
    ), Index(Contract.Favourite.COLUMN_NAME_ID_MOVIE, unique = true)],
)
data class FavouriteEntity(
    @ColumnInfo(name = Contract.Favourite.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = Contract.Favourite.COLUMN_NAME_ID_MOVIE)
    val idMovie: Int,

    @ColumnInfo(name = Contract.Favourite.COLUMN_NAME_TITLE)
    val title: String,
)