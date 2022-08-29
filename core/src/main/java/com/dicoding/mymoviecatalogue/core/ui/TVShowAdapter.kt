package com.dicoding.mymoviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mymoviecatalogue.core.BuildConfig.POSTER_URL
import com.dicoding.mymoviecatalogue.core.R
import com.dicoding.mymoviecatalogue.core.databinding.ItemsTvBinding
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow
import com.dicoding.mymoviecatalogue.core.utils.TvDiffCallback

class TVShowAdapter(private val callback: FragmentTvCallback) : RecyclerView.Adapter<TVShowAdapter.TvViewHolder>() {

    private var listData = ArrayList<TvShow>()
    var onItemClick: ((TvShow) -> Unit)? = null

    fun setData(newListData: List<TvShow>?){
        if(newListData == null)return
        val diffCallback = TvDiffCallback(listData,newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding = ItemsTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = listData[position]
        holder.bind(tv)
    }

    inner class TvViewHolder(private val binding: ItemsTvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvShow){
            with(binding){
                tvItemTitle.text = tv.name
                ratingBar.rating = tv.voteAverage.toFloat()/2

                imgShare.setOnClickListener { callback.onShareClick(tv) }
                Glide.with(itemView.context)
                        .load(POSTER_URL + tv.posterPath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }
}