package com.dicoding.mymoviecatalogue.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase
import com.dicoding.mymoviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.dicoding.mymoviecatalogue.favorite.tvshow.FavoriteTvViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movieTvUseCase: MovieTvUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(movieTvUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                FavoriteTvViewModel(movieTvUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}