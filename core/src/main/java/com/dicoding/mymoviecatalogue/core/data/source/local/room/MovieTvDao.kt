package com.dicoding.mymoviecatalogue.core.data.source.local.room

import androidx.room.*
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM movie_entities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvShow_entities")
    fun getTv(): Flow<List<TvShowEntity>>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvShow_entities WHERE id = :id")
    fun getTvById(id: Int): Flow<TvShowEntity>

    @Transaction
    @Query("SELECT * FROM movie_detail_entities WHERE id = :id")
    fun getMovieDetail(id: Int): Flow<MovieDetailEntity>

    @Transaction
    @Query("SELECT * FROM tvshow_detail_entities WHERE id = :id")
    fun getTvDetail(id: Int): Flow<TvShowDetailEntity>

    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvShow_entities WHERE is_favorite = 1")
    fun getFavoriteTv(): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movies: List<MovieDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(item: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM movie_entities WHERE id = :id and is_favorite = 1)")
    fun getFavoriteMovieById(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM tvShow_entities WHERE id = :id and is_favorite = 1)")
    fun getFavoriteTvById(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM favorite_entity WHERE id = :id)")
    fun getFavorites(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tv: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvDetails(movies: List<TvShowDetailEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTv(tv: TvShowEntity)

    @Query("DELETE FROM favorite_entity WHERE id = :id")
    fun deleteFavorite(id: Int)
}