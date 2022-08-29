package com.dicoding.mymoviecatalogue.core.ui

import com.dicoding.mymoviecatalogue.core.domain.model.Movie
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow

interface FragmentCallback {
    fun onShareClick(movie: Movie)

}

interface FragmentTvCallback{
    fun onShareClick(tv: TvShow)
}
