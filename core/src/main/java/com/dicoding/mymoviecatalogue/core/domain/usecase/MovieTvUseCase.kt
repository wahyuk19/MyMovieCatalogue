package com.dicoding.mymoviecatalogue.core.domain.usecase

import com.dicoding.mymoviecatalogue.core.domain.model.*
import com.dicoding.mymoviecatalogue.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getTvShows(): Flow<Resource<List<TvShow>>>

    fun getMovieDetails(id: Int): Flow<Resource<Movie>>

    fun getTvDetails(id: Int): Flow<Resource<TvShow>>

    fun getExtMovieDetails(id: Int): Flow<Resource<MovieDetails>>

    fun getExtTvDetails(id: Int): Flow<Resource<TvShowDetails>>

    fun getFavMovie():  Flow<List<Movie>>

    fun getFavTv(): Flow<List<TvShow>>

    fun setFavMovie(movie: Movie, state: Boolean)

    fun setFavTv(tv: TvShow, state: Boolean)

    suspend fun insertFavorite(data: Favorite)

    fun getFavoriteMovieById(id: Int):Boolean

    fun getFavoriteTvById(id: Int):Boolean

    fun getFavorites(id: Int):Boolean

    fun deleteFavorite(id: Int)
}