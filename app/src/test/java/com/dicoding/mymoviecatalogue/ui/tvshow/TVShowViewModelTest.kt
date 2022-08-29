package com.dicoding.mymoviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
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
class TVShowViewModelTest {

    private lateinit var viewModel: TVShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTVRepository: MovieTVRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp(){
        viewModel = TVShowViewModel(movieTVRepository)
    }

    @Test
    fun getTv() {
        val dummyTv = Resource.success(pagedList)
        `when`(dummyTv.data?.size).thenReturn(3)
        val tv = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tv.value = dummyTv

        `when`(movieTVRepository.getTvShows()).thenReturn(tv)
        val tvEntities = viewModel.getTv().value?.data
        verify(movieTVRepository).getTvShows()
        assertNotNull(tvEntities)
        assertEquals(3, tvEntities?.size)

        viewModel.getTv().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}