package com.tmdb.app.features.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmdb.app.AppBundles
import com.tmdb.app.AppCache
import com.tmdb.app.AppConfig
import com.tmdb.app.data.models.Contents
import com.tmdb.app.data.repository.HomeRepository
import com.tmdb.app.helper.Helper
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(val repository: HomeRepository) : ViewModel(){

    val movieTitle = MutableLiveData<String>()
    val movieImg = MutableLiveData<String>()
    val movieOverview = MutableLiveData<String>()
    val language = MutableLiveData<String>()
    val genres = MutableLiveData<String>()
    val releaseDate = MutableLiveData<String>()
    val rating = MutableLiveData<String>()

    /**
     * Method to set the movie details
     */
    fun setMovieDetails(movie: Contents){
        movieTitle.value = movie.originalTitle
        movieOverview.value = movie.overview
        movieImg.value = AppConfig.IMAGE_URL + movie.backdropPath
        rating.value = movie.vote.toString() + "/10"
        language.value = movie.originalLanguage
        movie.releaseDate?.let {
            releaseDate.value = Helper.formatDate(AppBundles.DATE_FORMAT_1, AppBundles.DATE_FORMAT_2, it)
        }
        val genreNameList = movie.genre?.map { genreId -> AppCache.genreList.value?.single { it.id == genreId } }
        genreNameList?.let {
            genres.value = it.map { it?.name }.toString().replace("[", "").replace("]", "")
        }
    }
}