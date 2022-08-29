package com.dicoding.mymoviecatalogue.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.mymoviecatalogue.core.domain.model.Movie

class MovieDiffCallback(private val oldList: List<Movie>,private val newList: List<Movie>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].movieId == newList[newItemPosition].movieId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovieList = oldList[oldItemPosition]
        val newMovieList = newList[newItemPosition]

        return if (oldMovieList.isFavorite != newMovieList.isFavorite) oldMovieList.isFavorite
        else newMovieList.isFavorite
    }

}