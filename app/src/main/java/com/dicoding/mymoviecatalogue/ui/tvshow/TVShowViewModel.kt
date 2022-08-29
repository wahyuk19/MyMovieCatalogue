package com.dicoding.mymoviecatalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mymoviecatalogue.core.domain.usecase.MovieTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel @Inject constructor(private val movieTVUseCase: MovieTvUseCase): ViewModel() {
    fun getTv() = movieTVUseCase.getTvShows().asLiveData()
}