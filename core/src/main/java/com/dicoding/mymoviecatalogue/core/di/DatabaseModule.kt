package com.dicoding.mymoviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.mymoviecatalogue.core.data.source.local.room.MovieTvDao
import com.dicoding.mymoviecatalogue.core.data.source.local.room.MovieTvDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieTvDatabase = Room.databaseBuilder(
        context,
        MovieTvDatabase::class.java,"MovieTv.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieTvDao(database: MovieTvDatabase):MovieTvDao = database.movieTvDao()
}