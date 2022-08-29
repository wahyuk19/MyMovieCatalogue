package com.dicoding.mymoviecatalogue.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mymoviecatalogue.R
import com.dicoding.mymoviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.mymoviecatalogue.core.domain.model.TvShow
import com.dicoding.mymoviecatalogue.core.ui.FragmentTvCallback
import com.dicoding.mymoviecatalogue.core.data.Resource
import com.dicoding.mymoviecatalogue.core.ui.TVShowAdapter
import com.dicoding.mymoviecatalogue.ui.detail.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVShowFragment : Fragment(), FragmentTvCallback {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TVShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvAdapter = TVShowAdapter(this)
        tvAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_TV, selectedData.tvId)
            startActivity(intent)
        }

        viewModel.getTv().observe(viewLifecycleOwner) { tv ->
            if (tv != null) {
                when (tv) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        tvAdapter.setData(tv.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, R.string.failure, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShareClick(tv: TvShow) {
        if(activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan aplikasi ini sekarang.")
                    .setText(resources.getString(R.string.share_text, tv.name))
                    .startChooser()
        }
    }

}