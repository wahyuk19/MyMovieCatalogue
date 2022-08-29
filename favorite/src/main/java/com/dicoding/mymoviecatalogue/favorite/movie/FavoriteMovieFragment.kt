package com.dicoding.mymoviecatalogue.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mymoviecatalogue.core.domain.model.Movie
import com.dicoding.mymoviecatalogue.core.ui.FavoriteMovieAdapter
import com.dicoding.mymoviecatalogue.core.ui.FragmentCallback
import com.dicoding.mymoviecatalogue.di.FavModuleDependencies
import com.dicoding.mymoviecatalogue.favorite.DaggerFavComponent
import com.dicoding.mymoviecatalogue.favorite.R
import com.dicoding.mymoviecatalogue.favorite.ViewModelFactory
import com.dicoding.mymoviecatalogue.favorite.databinding.FragmentFavoriteMovieBinding
import com.dicoding.mymoviecatalogue.ui.detail.DetailMovieActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieFragment : Fragment(), FragmentCallback {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val fragmentFavoriteMovieBinding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteMovieViewModel by viewModels{
        factory
    }
    private lateinit var favoriteAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerFavComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext, FavModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        _binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(fragmentFavoriteMovieBinding.rvMovieFavorite)
        if (activity != null) {

            favoriteAdapter = FavoriteMovieAdapter(this)
            favoriteAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData.movieId)
                startActivity(intent)
            }
            fragmentFavoriteMovieBinding.progressBarFavorite.visibility = View.VISIBLE
            viewModel.getMovies().observe(viewLifecycleOwner) { movie ->
                fragmentFavoriteMovieBinding.progressBarFavorite.visibility = View.GONE
                favoriteAdapter.setData(movie)
            }

            with(fragmentFavoriteMovieBinding.rvMovieFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShareClick(movie: Movie) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(resources.getString(R.string.share_text, movie.title))
                .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if(view != null){
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val movieEntity = favoriteAdapter.getSwipeData(swipedPosition)
                movieEntity.let {
                    viewModel.setFavorite(it)
                    lifecycleScope.launch(Dispatchers.Default){
                        viewModel.deleteFavorite(it.movieId)
                    }
                }

                val snackbar = Snackbar.make(view as View,R.string.item_deleted,Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

    })
}