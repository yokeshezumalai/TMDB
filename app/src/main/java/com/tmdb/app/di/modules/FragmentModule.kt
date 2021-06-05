package com.tmdb.app.di.modules

import com.tmdb.app.features.dashboard.HomeFragment
import com.tmdb.app.features.dashboard.MovieDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment
}