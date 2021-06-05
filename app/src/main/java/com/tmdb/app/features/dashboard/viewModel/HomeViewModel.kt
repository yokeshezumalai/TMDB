package com.tmdb.app.features.dashboard.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tmdb.app.AppCache
import com.tmdb.app.data.base.ApiObserver
import com.tmdb.app.data.models.*
import com.tmdb.app.data.repository.HomeRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: HomeRepository) : ViewModel(){

    fun getMovieDetails(owner: LifecycleOwner, result: UIResultListener<List<Contents>?>, page: Int) {

        if (AppCache.getMovieDetails()[page.toString()].isNullOrEmpty()){
            repository.getMovies(page)?.observe(owner, ApiObserver(object : ApiObserver.ChangeListener<String>{
                override fun onSuccess(dataWrapper: Envelope<String>?) {
                    dataWrapper?.data?.let {
                        val mainObject = JSONObject(it)
                        val results = Results().getMovies(mainObject.getJSONArray("results").toString())
                        val totalPages = mainObject.getString("total_pages")
                        val totalContents = mainObject.getString("total_results")

                        results?.let {
                            AppCache.putMovieDetails(page.toString(), it)
                            AppCache.totalPage.value = totalPages
                            AppCache.totalContents.value = totalContents
                            result.onReady(it)
                        }
                    }
                }

                override fun onFailed() {
                    result.onError()
                }

            }))
        }
        else {
            result.onReady(AppCache.getMovieDetails()[page.toString()])
        }
    }

    fun getGenresDetails(owner: LifecycleOwner) {
        if(AppCache.genreList.value.isNullOrEmpty()){
            repository.getGenres()?.observe(owner, ApiObserver(object : ApiObserver.ChangeListener<String>{
                override fun onSuccess(dataWrapper: Envelope<String>?) {
                    dataWrapper?.data?.let {
                        val mainObject = JSONObject(it)
                        val genres = GenreDetails().getGenres(mainObject.getJSONArray("genres").toString())

                        genres?.let {
                            AppCache.genreList.value = it
                        }
                    }
                }
                override fun onFailed() {}
            }))
        }
    }
}