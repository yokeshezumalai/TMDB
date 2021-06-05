package com.tmdb.app.features.dashboard.adapter

import android.content.res.Configuration
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.app.AppConfig
import com.tmdb.app.R
import com.tmdb.app.data.models.Contents
import com.tmdb.app.helper.loadUrl
import kotlinx.android.synthetic.main.cell_movie_items.view.*
import java.util.*


class MoviesListAdapter(list: List<Contents>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    var localItemList: List<Contents> = arrayListOf()

    var listItems: List<Contents>? = null

    init {
        this.localItemList = list
        this.listItems = list
    }

    fun setItems(list: ArrayList<Contents>) {
        localItemList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_movie_items, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return localItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = localItemList[position]
        val context = holder.movieName.context

        holder.movieName.text = data.originalTitle
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val imgUrl = data.backdropPath
            holder.movieImg.loadUrl(AppConfig.IMAGE_URL + imgUrl)
        } else {
            //portrait
            val imgUrl = data.posterPath
            holder.movieImg.loadUrl(AppConfig.IMAGE_URL + imgUrl)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.movieName
        val movieImg: ImageView = itemView.movieImg
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                var filterResults: FilterResults? = null
                listItems?.let {
                    val charString = charSequence.toString()
                    localItemList = if (charString.isEmpty()) {
                        it
                    } else {
                        getFilterItems(it, charString) as List<Contents>
                    }

                    filterResults = FilterResults()
                    filterResults?.values = localItemList

                }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filterResults.values?.let {
                    localItemList = it as List<Contents>
                }

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    private fun getFilterItems(items: List<Contents>, charString: String): List<Any> {


        val filteredList: List<Contents>
        if (TextUtils.isEmpty(charString)) {
            localItemList = items
        } else {
            filteredList = items.filter {
                it.title!!.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))
            }
            localItemList = filteredList
        }
        return localItemList
    }
}