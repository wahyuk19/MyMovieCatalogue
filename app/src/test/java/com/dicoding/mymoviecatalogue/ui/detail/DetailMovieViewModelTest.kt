package com.dicoding.mymoviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.mymoviecatalogue.core.data.MovieTVRepository
import com.dicoding.mymoviecatalogue.core.data.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var  viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateMovie()[0]
    private val dummyMovieId = DataDummy.generateMovie()[0].movieId
    private val dummyMovieDetail = DataDummy.generateDetailMovie()[0]
    private val dummyMovieDetailId = DataDummy.generateDetailMovie()[0].movieId
    private val dummyTVShow = DataDummy.generateTV()[0]
    private val dummyTVShowId = DataDummy.generateTV()[0].tvId
    private val dummyTVShowDetail = DataDummy.generateDetailTv()[0]
    private val dummyTVShowDetailId = DataDummy.generateDetailTv()[0].tvId
    private val movieId = dummyMovie.movieId
    private val tvId = dummyTVShow.tvId
    private val movieDetailId = dummyMovieDetail.movieId
    private val tvDetailId = dummyTVShowDetail.tvId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTVRepository: MovieTVRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerTv: Observer<Resource<TvShowEntity>>

    @Mock
    private lateinit var observerMovieDetail: Observer<Resource<MovieDetailEntity>>

    @Mock
    private lateinit var observerTvDetail: Observer<Resource<TvShowDetailEntity>>

    @Before
    fun setUp(){
        viewModel = DetailMovieViewModel(movieTVRepository)
    }

    @Test
    fun getMovieWithDetail(){
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovie)

        `when`(movieId?.let { movieTVRepository.getMovieDetails(it) }).thenReturn(expected)
        if (dummyMovieId != null) {
            viewModel.setSelectedMovieTv(dummyMovieId)
        }
        viewModel.getMovie.observeForever(observer)

        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovie.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getTvWithDetail(){
        val expected = MutableLiveData<Resource<TvShowEntity>>()
        expected.value = Resource.success(dummyTVShow)

        `when`(tvId?.let { movieTVRepository.getTvDetails(it) }).thenReturn(expected)
       if(dummyTVShowId != null){
           viewModel.setSelectedMovieTv(dummyTVShowId)
       }
        viewModel.getTV.observeForever(observerTv)

        verify(observerTv).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTV.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getMovieDetailExt(){
        val expected = MutableLiveData<Resource<MovieDetailEntity>>()
        expected.value = Resource.success(dummyMovieDetail)

        `when`(movieDetailId?.let { movieTVRepository.getExtMovieDetails(it) }).thenReturn(expected)
        if (dummyMovieDetailId != null) {
            viewModel.setSelectedMovieTv(dummyMovieDetailId)
        }
        if (dummyMovieDetailId != null) {
            viewModel.getDetaiMovie(dummyMovieDetailId).observeForever(observerMovieDetail)
        }

        verify(observerMovieDetail).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = dummyMovieDetailId?.let { viewModel.getDetaiMovie(it).value }

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getTvDetailExt(){
        val expected = MutableLiveData<Resource<TvShowDetailEntity>>()
        expected.value = Resource.success(dummyTVShowDetail)

        `when`(tvDetailId?.let { movieTVRepository.getExtTvDetails(it) }).thenReturn(expected)
        if(dummyTVShowDetailId != null){
            viewModel.setSelectedMovieTv(dummyTVShowDetailId)
        }
        if (dummyTVShowDetailId != null) {
            viewModel.getDetaiTv(dummyTVShowDetailId).observeForever(observerTvDetail)
        }

        verify(observerTvDetail).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = dummyTVShowDetailId?.let { viewModel.getDetaiTv(it).value }

        assertEquals(expectedValue, actualValue)
    }

}
