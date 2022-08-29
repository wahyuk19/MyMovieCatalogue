package com.dicoding.mymoviecatalogue.ui.detail

import androidx.lifecycle.*
import com.dicoding.mymoviecatalogue.core.domain.model.*
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieTVUseCase: MovieTvUseCase) : ViewModel() {
    private var movieTvId = MutableLiveData<Int>()

    fun setSelectedMovieTv(movieTvId: Int) {
        this.movieTvId.value = movieTvId
    }

    var getMovie = Transformations.switchMap(movieTvId){
        movieTVUseCase.getMovieDetails(it).asLiveData()
    }

    var getTV = Transformations.switchMap(movieTvId){
        movieTVUseCase.getTvDetails(it).asLiveData()
    }

    fun getDetaiMovie(id: Int) = movieTVUseCase.getExtMovieDetails(id).asLiveData()

    fun getDetaiTv(id: Int) = movieTVUseCase.getExtTvDetails(id).asLiveData()

    fun getFavoriteMovieById(id: Int): Boolean = movieTVUseCase.getFavoriteMovieById(id)

    fun getFavoriteTvById(id: Int): Boolean = movieTVUseCase.getFavoriteTvById(id)

    suspend fun insertFavorite(data: Favorite) =
        movieTVUseCase.insertFavorite(data)

    fun deleteFavorite(id: Int) = movieTVUseCase.deleteFavorite(id)

    fun setFavoriteMovie(){
        val movieRes = getMovie.value
        if (movieRes != null) {
            val movie = movieRes.data

            if (movie != null) {
                val newState = !movie.isFavorite
                movieTVUseCase.setFavMovie(movie, newState)
            }
        }
    }

    fun setFavoriteTv(){
        val tvRes = getTV.value
        if (tvRes != null) {
            val tv = tvRes.data

            if (tv != null) {
                val newState = !tv.isFavorite
                movieTVUseCase.setFavTv(tv, newState)
            }
        }
    }
}