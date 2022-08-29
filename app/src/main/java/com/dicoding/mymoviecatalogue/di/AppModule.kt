package com.dicoding.mymoviecatalogue.di

import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvInteractor
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieTvUseCase(movieTvInteractor: MovieTvInteractor): MovieTvUseCase
}