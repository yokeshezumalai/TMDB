package com.tmdb.app.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ContentDetails() : Parcelable {

    var content: List<Contents>?= null

    constructor(parcel: Parcel) : this() {
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    companion object CREATOR : Parcelable.Creator<ContentDetails> {
        override fun createFromParcel(parcel: Parcel): ContentDetails {
            return ContentDetails(parcel)
        }

        override fun newArray(size: Int): Array<ContentDetails?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}