package com.tmdb.app.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GenreDetails() : Parcelable {

    var id: Int? = null
    var name: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        id?.let { dest?.writeInt(it) }
        dest?.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<GenreDetails> {
        override fun createFromParcel(parcel: Parcel): GenreDetails {
            return GenreDetails(parcel)
        }

        override fun newArray(size: Int): Array<GenreDetails?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getGenres(dataStr: String): List<GenreDetails>? {
        val listType: Type = object : TypeToken<ArrayList<GenreDetails?>?>() {}.type
        val data: List<GenreDetails> = Gson().fromJson(dataStr, listType)
        return data
    }
}