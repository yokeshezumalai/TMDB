package com.tmdb.app.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Results() : Parcelable {

    @SerializedName("total_pages")
    var totalPages: Int?= null

    constructor(parcel: Parcel) : this() {
        totalPages = parcel.readInt()
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        totalPages?.let { dest?.writeInt(it) }
    }

    companion object CREATOR : Parcelable.Creator<Results> {
        override fun createFromParcel(parcel: Parcel): Results {
            return Results(parcel)
        }

        override fun newArray(size: Int): Array<Results?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getMovies(dataStr: String): List<Contents>? {
        val listType: Type = object : TypeToken<ArrayList<Contents?>?>() {}.type
        val data: List<Contents> = Gson().fromJson(dataStr, listType)
        return data
    }
}