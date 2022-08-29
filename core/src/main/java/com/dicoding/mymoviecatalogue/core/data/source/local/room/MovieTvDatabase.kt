package com.dicoding.mymoviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.*

@Database(entities = [FavoriteEntity::class,MovieEntity::class,TvShowEntity::class,MovieDetailEntity::class,TvShowDetailEntity::class],
        version = 1,
        exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract  fun movieTvDao(): MovieTvDao

}