package com.dicoding.mymoviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.mymoviecatalogue.core.data.source.local.entity.TvShowEntity
import com.dicoding.mymoviecatalogue.core.data.MovieTVRepository
import com.dicoding.mymoviecatalogue.favorite.tvshow.FavoriteTvViewModel
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
class FavoriteTvViewModelTest {
    private lateinit var viewModel: com.dicoding.mymoviecatalogue.favorite.tvshow.FavoriteTvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTVRepository

    @Mock
    private lateinit var observer : Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel =
            com.dicoding.mymoviecatalogue.favorite.tvshow.FavoriteTvViewModel(movieTvRepository)
    }

    @Test
    fun getFavorite() {
        val dummyTv = pagedList
        `when`(dummyTv.size).thenReturn(3)
        val tv = MutableLiveData<PagedList<TvShowEntity>>()
        tv.value = dummyTv

        `when`(movieTvRepository.getFavTv()).thenReturn(tv)
        val tvEntities = viewModel.getTv().value
        verify(movieTvRepository).getFavTv()
        assertNotNull(tvEntities)
        assertEquals(3, tvEntities?.size)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}