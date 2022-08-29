package com.dicoding.mymoviecatalogue.core.di

import com.dicoding.mymoviecatalogue.core.data.MovieTVRepository
import com.dicoding.mymoviecatalogue.core.domain.repository.IMovieTVRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class,DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieTVRepository: MovieTVRepository): IMovieTVRepository
}