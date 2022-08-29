package com.dicoding.mymoviecatalogue.favorite.tvshow

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
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow
import com.dicoding.mymoviecatalogue.core.ui.FavoriteTvAdapter
import com.dicoding.mymoviecatalogue.core.ui.FragmentTvCallback
import com.dicoding.mymoviecatalogue.di.FavModuleDependencies
import com.dicoding.mymoviecatalogue.favorite.DaggerFavComponent
import com.dicoding.mymoviecatalogue.favorite.R
import com.dicoding.mymoviecatalogue.favorite.ViewModelFactory
import com.dicoding.mymoviecatalogue.favorite.databinding.FragmentFavoriteTvBinding
import com.dicoding.mymoviecatalogue.ui.detail.DetailMovieActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvFragment : Fragment(), FragmentTvCallback {

    @Inject
    lateinit var factory: ViewModelFactory

    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteTvViewModel by viewModels{factory}
    private lateinit var favoriteAdapter: FavoriteTvAdapter

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
        _binding = FragmentFavoriteTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvTvFavorite)
        if (activity != null) {

            favoriteAdapter = FavoriteTvAdapter(this)
            favoriteAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_TV, selectedData.tvId)
                startActivity(intent)
            }
            binding.progressBarTvFavorite.visibility = View.VISIBLE
            viewModel.getTv().observe(viewLifecycleOwner) { tv ->
                binding.progressBarTvFavorite.visibility = View.GONE
                favoriteAdapter.setData(tv)
            }

            with(binding.rvTvFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = favoriteAdapter
            }
        }
    }

    override fun onShareClick(tv: TvShow) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(resources.getString(R.string.share_text, tv.name))
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
                val tvEntity = favoriteAdapter.getSwipeData(swipedPosition)
                tvEntity.let {
                    viewModel.setFavorite(it)
                    lifecycleScope.launch(Dispatchers.Default){
                        viewModel.deleteFavorite(it.tvId)
                    }
                }

                val snackbar = Snackbar.make(view as View,R.string.item_deleted,Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

    })
}