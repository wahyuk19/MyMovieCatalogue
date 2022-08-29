package com.dicoding.mymoviecatalogue.core.data.source.local

import com.dicoding.mymoviecatalogue.core.data.source.local.entity.*
import com.dicoding.mymoviecatalogue.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val mMovieTvDao: MovieTvDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = mMovieTvDao.getMovies()

    fun getAllTv(): Flow<List<TvShowEntity>> = mMovieTvDao.getTv()

    fun getMovieWithId(movieId: Int): Flow<MovieEntity> = mMovieTvDao.getMovieById(movieId)

    fun getTvWithId(TvId: Int): Flow<TvShowEntity> = mMovieTvDao.getTvById(TvId)

    fun getMovieDetails(movieId: Int): Flow<MovieDetailEntity> = mMovieTvDao.getMovieDetail(movieId)

    fun getTvDetails(TvId: Int): Flow<TvShowDetailEntity> = mMovieTvDao.getTvDetail(TvId)

    suspend fun insertMovie(movie: List<MovieEntity>) = mMovieTvDao.insertMovies(movie)

    suspend fun insertMovieDetail(movie: List<MovieDetailEntity>) = mMovieTvDao.insertMovieDetails(movie)

    suspend fun insertFavorite(data: FavoriteEntity) = mMovieTvDao.insertFavorite(data)

    suspend fun insertTv(tv: List<TvShowEntity>) = mMovieTvDao.insertTv(tv)

    suspend fun insertTvDetail(tv: List<TvShowDetailEntity>) = mMovieTvDao.insertTvDetails(tv)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = mMovieTvDao.getFavoriteMovie()

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        mMovieTvDao.updateMovie(movie)
    }

    fun getFavoriteTv(): Flow<List<TvShowEntity>> = mMovieTvDao.getFavoriteTv()

    fun setFavoriteTv(tv: TvShowEntity, newState: Boolean){
        tv.isFavorite = newState
        mMovieTvDao.updateTv(tv)
    }

    fun getFavoriteMovieById(id: Int):Boolean = mMovieTvDao.getFavoriteMovieById(id)

    fun getFavoriteTvById(id: Int):Boolean = mMovieTvDao.getFavoriteTvById(id)

    fun getFavorites(id: Int):Boolean = mMovieTvDao.getFavorites(id)

    fun deleteFavorite(id: Int) = mMovieTvDao.deleteFavorite(id)

}