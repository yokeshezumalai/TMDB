package com.tmdb.app

import androidx.lifecycle.MutableLiveData
import com.tmdb.app.data.models.Contents
import com.tmdb.app.data.models.GenreDetails

object AppCache {

    enum class Type {
        MOVIE_DETAILS,
        GENRE_DETAILS,
    }

    val data: HashMap<Type, MutableLiveData<Any>> = HashMap()

    private fun put(key: Type, value: Any) {
        if (data.containsKey(key)) {
            data[key]?.value = value
        } else {
            data[key] = MutableLiveData(value)
        }
    }

    private fun get(key: Type): Any? = data[key]?.value

    private fun <T> get(key: Type): MutableLiveData<T> {
        if (!data.containsKey(key)) {
            data[key] = MutableLiveData<Any>(null)
        }

        return data[key] as MutableLiveData<T>
    }

    fun remove(key: Type) = data.remove(key)

    fun clear() {
        remove(Type.MOVIE_DETAILS)
        remove(Type.GENRE_DETAILS)
        totalPage.value = null
        totalContents.value = null
    }

    fun putMovieDetails(page: String, data: List<Contents>) {
        val movies = getMovieDetails()
        movies[page] = data
        put(Type.MOVIE_DETAILS, movies)
    }

    fun getMovieDetails(): HashMap<String, List<Contents>> {
        return if ((get(Type.MOVIE_DETAILS)) != null){
            get(Type.MOVIE_DETAILS) as HashMap<String, List<Contents>>
        }else{
            HashMap()
        }
    }

    var totalPage = MutableLiveData<String>()

    var totalContents = MutableLiveData<String>()

    val genreList: MutableLiveData<List<GenreDetails>>
        get() = get<List<GenreDetails>>(Type.GENRE_DETAILS)


}
