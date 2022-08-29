package com.dicoding.mymoviecatalogue.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow

class TvDiffCallback(private val oldList: List<TvShow>, private val newList: List<TvShow>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].tvId == newList[newItemPosition].tvId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTvList = oldList[oldItemPosition]
        val newTvList = oldList[oldItemPosition]

        return if (oldTvList.isFavorite != newTvList.isFavorite) oldTvList.isFavorite
        else newTvList.isFavorite
    }

}