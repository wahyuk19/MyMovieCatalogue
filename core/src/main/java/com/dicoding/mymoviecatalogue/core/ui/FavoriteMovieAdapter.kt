package com.dicoding.mymoviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mymoviecatalogue.core.BuildConfig.POSTER_URL
import com.dicoding.mymoviecatalogue.core.R
import com.dicoding.mymoviecatalogue.core.databinding.ItemsMovieBinding
import com.dicoding.mymoviecatalogue.core.domain.model.Movie
import com.dicoding.mymoviecatalogue.core.utils.MovieDiffCallback

class FavoriteMovieAdapter(private val callback: FragmentCallback) : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>(){

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?){
        if(newListData == null) return
        val diffCallback = MovieDiffCallback(listData,newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val itemsFavoriteMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(itemsFavoriteMovieBinding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val movie = listData[position]
        holder.bind(movie)
    }

    fun getSwipeData(swipePosition: Int): Movie = listData[swipePosition]

    inner class FavoriteMovieViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvItemTitle.text = movie.title
                ratingBar.rating = movie.voteAverage.toFloat()/2
                imgShare.setOnClickListener { callback.onShareClick(movie) }
                Glide.with(itemView.context)
                    .load(POSTER_URL + movie.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading).override(100, 150)
                            .error(R.drawable.ic_error)
                    )
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