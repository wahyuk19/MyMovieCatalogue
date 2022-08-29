package com.dicoding.mymoviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase

class FavoriteTvViewModel(private val movieTVUseCase: MovieTvUseCase) : ViewModel() {
    fun getTv() = movieTVUseCase.getFavTv().asLiveData()

    fun setFavorite(tvShowEntity: TvShow){
        val newState = !tvShowEntity.isFavorite
        movieTVUseCase.setFavTv(tvShowEntity,newState)
    }

    fun deleteFavorite(id: Int?) = id?.let { movieTVUseCase.deleteFavorite(it) }
}