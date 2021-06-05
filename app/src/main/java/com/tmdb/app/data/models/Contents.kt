package com.tmdb.app.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Contents() : Parcelable {

    var id: Long? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("original_title")
    var originalTitle: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
    var title: String? = null
    @SerializedName("vote_average")
    var vote : Double?= null
    @SerializedName("release_date")
    var releaseDate: String?= null
    var adult: Boolean?= false
    var overview: String?= null
    @SerializedName("genre_ids")
    var genre: List<Int>?= null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        originalLanguage = parcel.readString()
        originalTitle = parcel.readString()
        backdropPath = parcel.readString()
        posterPath = parcel.readString()
        title = parcel.readString()
        vote = parcel.readDouble()
        releaseDate = parcel.readString()
        adult = parcel.readByte().toInt() != 0
        overview = parcel.readString()
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        id?.let { dest?.writeLong(it) }
        dest?.writeString(originalLanguage)
        dest?.writeString(originalTitle)
        dest?.writeString(backdropPath)
        dest?.writeString(posterPath)
        dest?.writeString(title)
        vote?.let { dest?.writeDouble(it) }
        dest?.writeString(releaseDate)
        adult?.let { dest?.writeByte((if (it) 1 else 0).toByte()) }
        dest?.writeString(overview)
    }

    companion object CREATOR : Parcelable.Creator<Contents> {
        override fun createFromParcel(parcel: Parcel): Contents {
            return Contents(parcel)
        }

        override fun newArray(size: Int): Array<Contents?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}