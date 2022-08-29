package com.dicoding.mymoviecatalogue.core.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.mymoviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.mymoviecatalogue.core.utils.AppExecutors
import com.dicoding.mymoviecatalogue.core.utils.LiveDataTestUtil
import com.dicoding.mymoviecatalogue.core.utils.PagedListUtil
import com.dicoding.mymoviecatalogue.core.data.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieTVRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieTVRepository = FakeMovieTVRepository(remote,local,appExecutors)

    private val movieResponse = DataDummy.getMovieResponse()
    private val tvResponse = DataDummy.getTvShowResponse()

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieTVRepository.getMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateMovie()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(),movieEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieTVRepository.getTvShows()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateTV()))
        verify(local).getAllTv()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size.toLong(),tvEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovies() {
        val id = DataDummy.generateMovie()[0].movieId as Int

        val dbDetail = MutableLiveData<MovieEntity>()
        dbDetail.value = DataDummy.generateMovie()[0]

        `when`(local.getMovieWithId(id)).thenReturn(dbDetail)

        val movieDetailEntities = LiveDataTestUtil.getValue(movieTVRepository.getMovieDetails(id))
        verify(local).getMovieWithId(id)
        assertNotNull(movieDetailEntities.data)
        assertEquals(dbDetail.value?.movieId, movieDetailEntities.data?.movieId)
        assertEquals(dbDetail.value?.title, movieDetailEntities.data?.title)
        assertEquals(dbDetail.value?.overview, movieDetailEntities.data?.overview)
        assertEquals(dbDetail.value?.voteAverage, movieDetailEntities.data?.voteAverage)
        assertEquals(dbDetail.value?.releaseDate, movieDetailEntities.data?.releaseDate)
        assertEquals(dbDetail.value?.backdropPath, movieDetailEntities.data?.backdropPath)
        assertEquals(dbDetail.value?.posterPath, movieDetailEntities.data?.posterPath)
    }

    @Test
    fun getDetailTvShows() {
        val id = DataDummy.generateTV()[0].tvId as Int

        val dbDetail = MutableLiveData<TvShowEntity>()
        dbDetail.value = DataDummy.generateTV()[0]

        `when`(local.getTvWithId(id)).thenReturn(dbDetail)

        val tvDetailEntities = LiveDataTestUtil.getValue(movieTVRepository.getTvDetails(id))
        verify(local).getTvWithId(id)
        assertNotNull(tvDetailEntities.data)
        assertEquals(dbDetail.value?.tvId, tvDetailEntities.data?.tvId)
        assertEquals(dbDetail.value?.name, tvDetailEntities.data?.name)
        assertEquals(dbDetail.value?.overview, tvDetailEntities.data?.overview)
        assertEquals(dbDetail.value?.voteAverage, tvDetailEntities.data?.voteAverage)
        assertEquals(dbDetail.value?.releaseDate, tvDetailEntities.data?.releaseDate)
        assertEquals(dbDetail.value?.backdropPath, tvDetailEntities.data?.backdropPath)
        assertEquals(dbDetail.value?.posterPath, tvDetailEntities.data?.posterPath)
    }

    @Test
    fun getFavoriteMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        movieTVRepository.getFavMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateMovie()))
        verify(local).getFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(),movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTv(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTv()).thenReturn(dataSourceFactory)
        movieTVRepository.getFavTv()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateTV()))
        verify(local).getFavoriteTv()
        assertNotNull(tvEntities)
        assertEquals(tvResponse.size.toLong(),tvEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailExtMovies() {
        val id = DataDummy.generateDetailMovie()[0].movieId as Int

        val dbDetail = MutableLiveData<MovieDetailEntity>()
        dbDetail.value = DataDummy.generateDetailMovie()[0]

        `when`(local.getMovieDetails(id)).thenReturn(dbDetail)

        val movieDetailExtEntities =
            LiveDataTestUtil.getValue(movieTVRepository.getExtMovieDetails(id))
        verify(local).getMovieDetails(id)
        assertNotNull(movieDetailExtEntities.data)
        assertEquals(dbDetail.value?.movieId, movieDetailExtEntities.data?.movieId)
        assertEquals(dbDetail.value?.runtime, movieDetailExtEntities.data?.runtime)
        assertEquals(dbDetail.value?.tagline, movieDetailExtEntities.data?.tagline)
    }

    @Test
    fun getDetailExtTv(){
        val id = DataDummy.generateDetailTv()[0].tvId as Int

        val dbDetail = MutableLiveData<TvShowDetailEntity>()
        dbDetail.value = DataDummy.generateDetailTv()[0]

        `when`(local.getTvDetails(id)).thenReturn(dbDetail)

        val tvDetailExtEntities =
            LiveDataTestUtil.getValue(movieTVRepository.getExtTvDetails(id))
        verify(local).getTvDetails(id)
        assertNotNull(tvDetailExtEntities.data)
        assertEquals(dbDetail.value?.tvId, tvDetailExtEntities.data?.tvId)
        assertEquals(dbDetail.value?.runtime, tvDetailExtEntities.data?.runtime)
        assertEquals(dbDetail.value?.tagline, tvDetailExtEntities.data?.tagline)
    }
}