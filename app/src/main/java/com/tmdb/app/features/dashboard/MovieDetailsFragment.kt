package com.tmdb.app.features.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tmdb.app.AppBundles
import com.tmdb.app.R
import com.tmdb.app.data.models.Contents
import com.tmdb.app.databinding.FragmentMovieDetailsBinding
import com.tmdb.app.di.Injectable
import com.tmdb.app.features.dashboard.viewModel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment(), Injectable {

    lateinit var viewModel: MovieDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentMovieDetailsBinding

    private var movieData: Contents? = null

    companion object {

        fun buildBundle(movieDetails: Contents?): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(AppBundles.DATA, movieDetails)
            return bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<FragmentMovieDetailsBinding>(inflater, R.layout.fragment_movie_details, container, false).also {
            binding = it
            viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieData = arguments?.getParcelable(AppBundles.DATA)

        backImg?.setOnClickListener { Navigation.findNavController(view).navigateUp() }

        bindViews()
    }

    /**
     * Method to Bind Views
     */
    private fun bindViews() {
        movieData?.let { movie ->
            viewModel.setMovieDetails(movie)
        }
    }
}