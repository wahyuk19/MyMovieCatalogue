package com.dicoding.mymoviecatalogue.ui.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mymoviecatalogue.BuildConfig.POSTER_URL
import com.dicoding.mymoviecatalogue.R
import com.dicoding.mymoviecatalogue.core.data.Resource
import com.dicoding.mymoviecatalogue.core.domain.model.Favorite
import com.dicoding.mymoviecatalogue.core.domain.model.Movie
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow
import com.dicoding.mymoviecatalogue.databinding.ActivityDetailMovieBinding
import com.dicoding.mymoviecatalogue.databinding.ContentDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var detailContentBinding: ContentDetailMovieBinding
    private lateinit var activityDetailMovieBinding: ActivityDetailMovieBinding
    private lateinit var broadcastReceiver: BroadcastReceiver

    private val viewModel: DetailMovieViewModel by viewModels()
    private var menu: Menu? = null
    private var movieData: Movie? = null
    private var tvData: TvShow? = null
    private var mainState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent

        setContentView(activityDetailMovieBinding.root)
        setSupportActionBar(activityDetailMovieBinding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE, 0)
            val tvId = extras.getInt(EXTRA_TV, 0)
            if (movieId != 0) {
                viewModel.setSelectedMovieTv(movieId)
                viewModel.getMovie.observe(this) { movie ->
                    if (movie != null) {
                        when (movie) {
                            is Resource.Loading -> activityDetailMovieBinding.detailContent.progressBar.visibility =
                                View.VISIBLE
                            is Resource.Success -> if (movie.data != null) {
                                activityDetailMovieBinding.detailContent.progressBar.visibility =
                                    View.GONE
                                activityDetailMovieBinding.detailContent.detailContent.visibility =
                                    View.VISIBLE

                                movieData = movie.data
                                movieData?.let { populateMovie(it) }
                            }
                            is Resource.Error -> {
                                activityDetailMovieBinding.detailContent.progressBar.visibility =
                                    View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    R.string.failure,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                supportActionBar?.title = getString(R.string.movie_details)
            } else if (tvId != 0) {
                viewModel.setSelectedMovieTv(tvId)
                viewModel.getTV.observe(this) { tv ->
                    if (tv != null) {
                        when (tv) {
                            is Resource.Loading -> activityDetailMovieBinding.detailContent.progressBar.visibility =
                                View.VISIBLE
                            is Resource.Success -> if (tv.data != null) {
                                activityDetailMovieBinding.detailContent.progressBar.visibility =
                                    View.GONE
                                activityDetailMovieBinding.detailContent.detailContent.visibility =
                                    View.VISIBLE

                                tvData = tv.data
                                tvData?.let { populateTv(it) }
                            }
                            is Resource.Error -> {
                                activityDetailMovieBinding.detailContent.progressBar.visibility =
                                    View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    R.string.failure,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                supportActionBar?.title = getString(R.string.tv_show_details)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIE, 0)
            val tvId = extras.getInt(EXTRA_TV, 0)
            if (movieId != 0) {
                viewModel.setSelectedMovieTv(movieId)
                lifecycleScope.launch(Dispatchers.Default) {
                    val state = viewModel.getFavoriteMovieById(movieId)
                    checkFavorite(state)
                }
            } else {
                viewModel.setSelectedMovieTv(tvId)
                lifecycleScope.launch(Dispatchers.Default) {
                    val state = viewModel.getFavoriteTvById(tvId)
                    checkFavorite(state)
                }
            }
        }
        return true
    }

    private fun checkFavorite(state: Boolean) {
        runOnUiThread {
            val menuItem = menu?.findItem(R.id.action_add_favorite)
            if (state) {
                menuItem?.icon = ContextCompat.getDrawable(
                    this@DetailMovieActivity,
                    R.drawable.ic_baseline_star_24
                )
                mainState = true
            } else {
                menuItem?.icon = ContextCompat.getDrawable(
                    this@DetailMovieActivity,
                    R.drawable.ic_baseline_star_outline_24
                )
                mainState = false
            }
        }
    }

    private fun setFavoriteState(
        state: Boolean,
        id: Int?,
        title: String?,
        posterPath: String?,
        type: String?
    ) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_add_favorite)
        val favorite = Favorite(id, title, posterPath, type)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24)
            mainState = true
            lifecycleScope.launch(Dispatchers.Default) {
                if (id != null) {
                    viewModel.insertFavorite(favorite)
                }
            }
        } else if (!state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_outline_24)
            mainState = false
            lifecycleScope.launch(Dispatchers.Default) {
                if (id != null) {
                    viewModel.deleteFavorite(id)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_favorite) {
            val extras = intent.extras
            if (extras != null) {
                val movieId = extras.getInt(EXTRA_MOVIE, 0)
                val tvId = extras.getInt(EXTRA_TV, 0)
                if (movieId != 0) {
                    viewModel.setFavoriteMovie()
                    if (!mainState)
                        setFavoriteState(
                            true,
                            movieData?.movieId,
                            movieData?.title,
                            movieData?.posterPath,
                            "Movie"
                        )
                    else
                        setFavoriteState(
                            false,
                            movieData?.movieId,
                            movieData?.title,
                            movieData?.posterPath,
                            "Movie"
                        )
                } else if (tvId != 0) {
                    viewModel.setFavoriteTv()
                    if (!mainState) {
                        setFavoriteState(
                            true,
                            tvData?.tvId,
                            tvData?.name,
                            tvData?.posterPath,
                            "Tv Show"
                        )
                    } else {
                        setFavoriteState(
                            false,
                            tvData?.tvId,
                            tvData?.name,
                            tvData?.posterPath,
                            "Tv Show"
                        )
                    }
                }
            }
            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateMovie(movieEntity: Movie) {
        detailContentBinding.apply {
            val imageSource = POSTER_URL + movieEntity.posterPath

            textTitle.text = movieEntity.title
            ratingBar.rating = movieEntity.voteAverage.toFloat() / 2
            textDescription.text = movieEntity.overview
            textYear.text = movieEntity.releaseDate

            Glide.with(this@DetailMovieActivity)
                .load(imageSource)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imagePoster)

            movieEntity.movieId.let {
                viewModel.getDetaiMovie(it).observe(this@DetailMovieActivity) { detail ->
                    val runtime = detail.data?.runtime.toString() + " Minutes"
                    textRuntime.text = runtime
                    textTagline.text = detail.data?.tagline
                }
            }

        }

    }

    private fun populateTv(tvEntity: TvShow) {
        detailContentBinding.apply {
            textTitle.text = tvEntity.name
            ratingBar.rating = tvEntity.voteAverage.toFloat() / 2
            textDescription.text = tvEntity.overview
            textYear.text = tvEntity.releaseDate

            Glide.with(this@DetailMovieActivity)
                .load(POSTER_URL + tvEntity.posterPath)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(detailContentBinding.imagePoster)

            tvEntity.tvId.let {
                viewModel.getDetaiTv(it).observe(this@DetailMovieActivity) { detail ->
                    val runtime = detail.data?.runtime.toString() + " Minutes"
                    textRuntime.text = runtime
                    textTagline.text = detail.data?.tagline
                }
            }
        }

    }

    private fun registerBroadCastReceiver(){
        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action){
                    Intent.ACTION_POWER_CONNECTED -> {
                        supportActionBar?.title = getString(R.string.power_connected)
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        supportActionBar?.title = getString(R.string.power_disconnected)
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver,intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV = "extra_tv"
    }

}