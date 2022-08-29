package com.dicoding.mymoviecatalogue.core.data

import com.dicoding.mymoviecatalogue.core.data.source.remote.api.ApiResponse
import com.dicoding.mymoviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.mymoviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.mymoviecatalogue.core.data.source.remote.response.*
import com.dicoding.mymoviecatalogue.core.domain.model.*
import com.dicoding.mymoviecatalogue.core.domain.repository.IMovieTVRepository
import com.dicoding.mymoviecatalogue.core.utils.AppExecutors
import com.dicoding.mymoviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieTVRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : IMovieTVRepository {

    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieItem>>(){
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map{DataMapper.mapEntitiesToDomainMovie(it)}
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovieList()

            override suspend fun saveCallResult(data: List<MovieItem>) {
                val movieList = DataMapper.mapResponseToEntitiesMovie(data)
                localDataSource.insertMovie(movieList)

            }
        }.asFlow()
    }

    override fun getTvShows(): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvItem>>(){
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTv().map{DataMapper.mapEntitiesToDomainTv(it)}
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvList()

            override suspend fun saveCallResult(data: List<TvItem>) {
                val tvList = DataMapper.mapResponseToEntitiesTv(data)
                localDataSource.insertTv(tvList)

            }
        }.asFlow()
    }


    override fun getMovieDetails(id: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, List<MovieItem>>(){
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieWithId(id).map { DataMapper.mapEntitiesToDomainMovieDetail(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null


            override suspend fun createCall(): Flow<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovieList()

            override suspend fun saveCallResult(data: List<MovieItem>) {
                val list = DataMapper.mapResponseToEntitiesMovieDetail(data)
                localDataSource.insertMovie(list)

                }

        }.asFlow()
    }

    override fun getTvDetails(id: Int): Flow<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, List<TvItem>>(){
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvWithId(id).map { DataMapper.mapEntitiesToDomainTvDetail(it) }
            }

            override fun shouldFetch(data: TvShow?): Boolean =
                data == null


            override suspend fun createCall(): Flow<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvList()

            override suspend fun saveCallResult(data: List<TvItem>) {
                val list = DataMapper.mapResponseToEntitiesTvDetail(data)
                localDataSource.insertTv(list)

            }

        }.asFlow()
    }

    override fun getExtMovieDetails(id: Int): Flow<Resource<MovieDetails>> {
        return object : NetworkBoundResource<MovieDetails, List<MovieDetailResponse>>(){
            override fun loadFromDB(): Flow<MovieDetails> {
                return localDataSource.getMovieDetails(id).map {
                    DataMapper.mapEntitiesToDomainExtMovie(it) }
            }

            override fun shouldFetch(data: MovieDetails?): Boolean =
               true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieDetailResponse>>> =
                remoteDataSource.getMovieAddDetails(id)

            override suspend fun saveCallResult(data: List<MovieDetailResponse>) {
                val list = DataMapper.mapResponseToEntitiesExtMovie(data)
                localDataSource.insertMovieDetail(list)
            }

        }.asFlow()
    }

    override fun getExtTvDetails(id: Int): Flow<Resource<TvShowDetails>> {
        return object : NetworkBoundResource<TvShowDetails, List<TvDetailResponse>>(){
            override fun loadFromDB(): Flow<TvShowDetails> {
                return localDataSource.getTvDetails(id).map {
                    DataMapper.mapEntitiesToDomainExtTv(it)
                }
            }

            override fun shouldFetch(data: TvShowDetails?): Boolean =
               true

            override suspend fun createCall(): Flow<ApiResponse<List<TvDetailResponse>>> =
                remoteDataSource.getTvAddDetails(id)

            override suspend fun saveCallResult(data: List<TvDetailResponse>) {
                val list = DataMapper.mapResponseToEntitiesExtTv(data)
                localDataSource.insertTvDetail(list)

            }

        }.asFlow()
    }

    override fun getFavMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomainMovie(it) }
    }

    override fun getFavTv(): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTv().map {
            DataMapper.mapEntitiesToDomainTv(it)
        }
    }

    override fun setFavMovie(movie: Movie, state: Boolean){
        val favoriteEntity = DataMapper.mapDomainToEntityMovie(movie)
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteMovie(favoriteEntity, state)
        }
    }

    override fun setFavTv(tv: TvShow, state: Boolean) {
        val favoriteEntity = DataMapper.mapDomainToEntityTv(tv)
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteTv(favoriteEntity, state)
        }
    }

    override suspend fun insertFavorite(data: Favorite) {
        val insert = DataMapper.mapDomainToEntityFavorite(data)
        localDataSource.insertFavorite(insert)
    }

    override fun getFavoriteMovieById(id: Int): Boolean {
        return localDataSource.getFavoriteMovieById(id)
    }

    override fun getFavoriteTvById(id: Int): Boolean {
        return localDataSource.getFavoriteTvById(id)
    }

    override fun getFavorites(id: Int): Boolean {
        return  localDataSource.getFavorites(id)
    }

    override fun deleteFavorite(id: Int) {
        localDataSource.deleteFavorite(id)
    }
}