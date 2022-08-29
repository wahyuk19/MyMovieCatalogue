package com.dicoding.mymoviecatalogue.core.domain.usecase

import com.dicoding.mymoviecatalogue.core.domain.model.*
import com.dicoding.mymoviecatalogue.core.domain.repository.IMovieTVRepository
import com.dicoding.mymoviecatalogue.core.data.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieTvInteractor @Inject constructor(private val movieTVRepository: IMovieTVRepository) : MovieTvUseCase{
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        movieTVRepository.getMovies()

    override fun getTvShows(): Flow<Resource<List<TvShow>>> =
        movieTVRepository.getTvShows()

    override fun getMovieDetails(id: Int): Flow<Resource<Movie>> =
        movieTVRepository.getMovieDetails(id)

    override fun getTvDetails(id: Int): Flow<Resource<TvShow>> =
        movieTVRepository.getTvDetails(id)

    override fun getExtMovieDetails(id: Int): Flow<Resource<MovieDetails>> =
        movieTVRepository.getExtMovieDetails(id)

    override fun getExtTvDetails(id: Int): Flow<Resource<TvShowDetails>> =
        movieTVRepository.getExtTvDetails(id)

    override fun getFavMovie(): Flow<List<Movie>> =
        movieTVRepository.getFavMovie()

    override fun getFavTv(): Flow<List<TvShow>> =
        movieTVRepository.getFavTv()

    override fun setFavMovie(movie: Movie, state: Boolean) =
        movieTVRepository.setFavMovie(movie,state)

    override fun setFavTv(tv: TvShow, state: Boolean) =
        movieTVRepository.setFavTv(tv,state)

    override suspend fun insertFavorite(data: Favorite) =
        movieTVRepository.insertFavorite(data)

    override fun getFavoriteTvById(id: Int): Boolean =
        movieTVRepository.getFavoriteTvById(id)

    override fun getFavoriteMovieById(id: Int) : Boolean =
        movieTVRepository.getFavoriteMovieById(id)

    override fun getFavorites(id: Int) : Boolean =
        movieTVRepository.getFavorites(id)

    override fun deleteFavorite(id: Int) =
        movieTVRepository.deleteFavorite(id)

}