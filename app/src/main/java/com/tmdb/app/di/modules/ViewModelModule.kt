package com.tmdb.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmdb.app.di.tmdbViewModelFactory
import com.tmdb.app.di.ViewModelKey
import com.tmdb.app.features.dashboard.viewModel.HomeViewModel
import com.tmdb.app.features.dashboard.viewModel.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: tmdbViewModelFactory): ViewModelProvider.Factory
}