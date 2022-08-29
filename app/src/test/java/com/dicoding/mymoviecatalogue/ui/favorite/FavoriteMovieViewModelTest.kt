package com.dicoding.mymoviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.mymoviecatalogue.core.data.MovieTVRepository
import com.dicoding.mymoviecatalogue.favorite.movie.FavoriteMovieViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {
    private lateinit var viewModel: com.dicoding.mymoviecatalogue.favorite.movie.FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTVRepository

    @Mock
    private lateinit var observer : Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel =
            com.dicoding.mymoviecatalogue.favorite.movie.FavoriteMovieViewModel(movieTvRepository)
    }

    @Test
    fun getFavorite() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(3)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieTvRepository.getFavMovie()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        verify(movieTvRepository).getFavMovie()
        assertNotNull(movieEntities)
        assertEquals(3, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}