package com.dicoding.mymoviecatalogue.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mymoviecatalogue.core.domain.model.Movie
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase

class FavoriteMovieViewModel(private val movieTVUseCase: MovieTvUseCase) : ViewModel() {
    fun getMovies() = movieTVUseCase.getFavMovie().asLiveData()

    fun setFavorite(movieEntity: Movie){
        val newState = !movieEntity.isFavorite
        movieTVUseCase.setFavMovie(movieEntity,newState)
    }

    fun deleteFavorite(id: Int?) = id?.let { movieTVUseCase.deleteFavorite(it) }
}