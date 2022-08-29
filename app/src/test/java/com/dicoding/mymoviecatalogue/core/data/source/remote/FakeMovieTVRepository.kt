package com.dicoding.mymoviecatalogue.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.mymoviecatalogue.core.data.source.remote.api.ApiResponse
import com.dicoding.mymoviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.*
import com.dicoding.mymoviecatalogue.core.data.source.remote.response.*
import com.dicoding.mymoviecatalogue.core.domain.repository.IMovieTVRepository
import com.dicoding.mymoviecatalogue.core.utils.AppExecutors
import com.dicoding.mymoviecatalogue.core.data.Resource

class FakeMovieTVRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : IMovieTVRepository {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<PagedList<MovieEntity>, List<MovieItem>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovieList()

            override fun saveCallResult(data: List<MovieItem>) {
                val movieList = ArrayList<MovieEntity>()
                for(response in data){
                    response.apply {
                        val movie = MovieEntity(
                            response.id,
                            response.title,
                            response.overview,
                            response.voteAverage,
                            response.releaseDate,
                            response.posterPath,
                            response.backdropPath
                        )
                        movieList.add(movie)
                    }

                }
                localDataSource.insertMovie(movieList)

            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<PagedList<TvShowEntity>, List<TvItem>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvList()

            override fun saveCallResult(data: List<TvItem>) {
                val tvList = ArrayList<TvShowEntity>()
                for(response in data){
                    response.apply {
                        val tv = TvShowEntity(
                            response.id,
                            response.name,
                            response.overview,
                            response.voteAverage,
                            response.firstAirDate,
                            response.posterPath,
                            response.backdropPath
                        )
                        tvList.add(tv)
                    }

                }
                localDataSource.insertTv(tvList)

            }
        }.asLiveData()
    }


    override fun getMovieDetails(id: Int): LiveData<Resource<MovieEntity>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<MovieEntity, List<MovieItem>>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieWithId(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null


            override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovieDetails(id)

            override fun saveCallResult(data: List<MovieItem>) {
                val list = ArrayList<MovieEntity>()
                for(response in data){
                    response.apply {
                        val detail = MovieEntity(
                            response.id,
                            response.title,
                            response.overview,
                            response.voteAverage,
                            response.releaseDate,
                            response.posterPath,
                            response.backdropPath
                        )

                        list.add(detail)
                    }

                }
                localDataSource.insertMovie(list)

            }

        }.asLiveData()
    }

    override fun getTvDetails(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<TvShowEntity, List<TvItem>>(appExecutors){
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvWithId(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null


            override fun createCall(): LiveData<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvDetails(id)

            override fun saveCallResult(data: List<TvItem>) {
                val list = ArrayList<TvShowEntity>()
                for(response in data){
                    response.apply {
                        val detail = TvShowEntity(
                            response.id,
                            response.name,
                            response.overview,
                            response.voteAverage,
                            response.firstAirDate,
                            response.posterPath,
                            response.backdropPath
                        )

                        list.add(detail)
                    }

                }
                localDataSource.insertTv(list)

            }

        }.asLiveData()
    }

    override fun getExtMovieDetails(id: Int): LiveData<Resource<MovieDetailEntity>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<MovieDetailEntity, List<MovieDetailResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<MovieDetailEntity> =
                localDataSource.getMovieDetails(id)

            override fun shouldFetch(data: MovieDetailEntity?): Boolean =
                data == null


            override fun createCall(): LiveData<ApiResponse<List<MovieDetailResponse>>> =
                remoteDataSource.getMovieAddDetails(id)

            override fun saveCallResult(data: List<MovieDetailResponse>) {
                val list = ArrayList<MovieDetailEntity>()
                for(response in data){
                    response.apply {
                        val detail = MovieDetailEntity(
                            response.id,
                            response.runtime,
                            response.tagline
                        )

                        list.add(detail)
                    }

                }
                localDataSource.insertMovieDetail(list)

            }

        }.asLiveData()
    }

    override fun getExtTvDetails(id: Int): LiveData<Resource<TvShowDetailEntity>> {
        return object : com.dicoding.mymoviecatalogue.core.data.NetworkBoundResource<TvShowDetailEntity, List<TvDetailResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<TvShowDetailEntity> =
                localDataSource.getTvDetails(id)

            override fun shouldFetch(data: TvShowDetailEntity?): Boolean =
                data == null


            override fun createCall(): LiveData<ApiResponse<List<TvDetailResponse>>> =
                remoteDataSource.getTvAddDetails(id)

            override fun saveCallResult(data: List<TvDetailResponse>) {
                val list = ArrayList<TvShowDetailEntity>()
                for(response in data){
                    response.apply {
                        val detail = TvShowDetailEntity(
                            response.id,
                            response.runtime[0],
                            response.tagline
                        )

                        list.add(detail)
                    }

                }
                localDataSource.insertTvDetail(list)

            }

        }.asLiveData()
    }

    override fun getFavMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(),config).build()
    }

    override fun getFavTv(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(),config).build()
    }

    override fun setFavMovie(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteMovie(movie, state)
        }

    override fun setFavTv(tv: TvShowEntity, state: Boolean) =
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteTv(tv, state)
        }

    override fun insertFavorite(data: FavoriteEntity) {
        localDataSource.insertFavorite(data)
    }

    override fun deleteFavorite(id: Int) {
        localDataSource.deleteFavorite(id)
    }
}