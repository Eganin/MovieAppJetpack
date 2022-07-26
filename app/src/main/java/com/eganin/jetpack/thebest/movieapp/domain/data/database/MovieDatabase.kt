package com.eganin.jetpack.thebest.movieapp.domain.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.FavouriteEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieDetailsEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.MovieEntity
import com.eganin.jetpack.thebest.movieapp.domain.data.models.entity.converter.Converters
import com.eganin.jetpack.thebest.movieapp.domain.data.models.network.entity.CastItem

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class, CastItem::class, FavouriteEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
    abstract val movieDetailsDao: MovieDetailsDao
    abstract val creditsDao: CreditsDao
    abstract val favouriteDao: FavouriteDao

    companion object {

        fun create(applicationContext: Context) =
            Room.databaseBuilder(
                applicationContext,
                MovieDatabase::class.java,
                Contract.DATABASE_NAME,
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}