package com.dicoding.mymoviecatalogue.di

import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavModuleDependencies {

    fun movieTvUseCase(): MovieTvUseCase
}