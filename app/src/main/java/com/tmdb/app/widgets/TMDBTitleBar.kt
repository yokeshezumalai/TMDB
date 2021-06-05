package com.tmdb.app.widgets

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.tmdb.app.R
import com.tmdb.app.helper.Helper
import com.tmdb.app.helper.onChange
import kotlinx.android.synthetic.main.custom_title_bar.view.*

class TMDBTitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var title: String? = null
    private var extraIcon: Drawable? = null
    private var extraIconShow = false
    private var backIconHide = false
    private var changeStartTitleFont = false
    private var searchText: EditText?= null
    private var startSearch: Boolean= false

    var backIconImageView: ImageView? = null

    private var callbackListener: Callback? = null

    init {

        prepareAttributes(attrs)
        prepareLayout()

    }

    private fun prepareAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val attrs = context.obtainStyledAttributes(it, R.styleable.TMDBTitleBar, 0, 0)
            try {
                title = attrs.getString(R.styleable.TMDBTitleBar_titleText)
                extraIcon = attrs.getDrawable(R.styleable.TMDBTitleBar_extraIcon)
                extraIconShow = attrs.getBoolean(R.styleable.TMDBTitleBar_extraIconShow, false)
                backIconHide = attrs.getBoolean(R.styleable.TMDBTitleBar_backIconHide, false)
                changeStartTitleFont =
                    attrs.getBoolean(R.styleable.TMDBTitleBar_changeStartTitleFont, false)
            } finally {
                attrs.recycle()
            }
        }
    }

    private fun prepareLayout() {
        val root = LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true)
        backIconImageView = rootView.findViewById(R.id.startIcon)
        searchText = rootView.findViewById(R.id.searchMovies)

        backIconImageView?.visibility = if (!backIconHide) View.VISIBLE else View.GONE

        setTextLayout(title)

        backIconImageView?.setOnClickListener {
            val navController = Navigation.findNavController(root)
            navController.navigateUp()
        }

        extraIconShow.let {
            if(it){
                startSearch = true
                rightIcon?.visibility = View.VISIBLE
            }else{
                rightIcon?.visibility = View.GONE
            }
        }
    }

    private fun setTextLayout(text: String?) {
        text?.let { title ->
            startTitleText.text = title
            startTitleText.visibility = View.VISIBLE
            if (changeStartTitleFont) {
                setStartTitleTypeFace()
            }
        }
    }


    @UiThread
    fun setTitleText(text: String?) {
        setTextLayout(text)
        requestLayout()
    }

    @UiThread
    fun setStartTitleTypeFace() {
        startTitleText.typeface =
            Typeface.createFromAsset(startTitleText.context?.assets, "fonts/avenir_light.ttf")
        startTitleText.textSize = 25f
    }

    fun setListener(listener: Callback) {
        callbackListener = listener

        callbackListener?.let {
            backIconImageView?.setOnClickListener {
                callbackListener?.onBackButtonClick()
            }
            rightIcon?.setOnClickListener {
                if(startSearch){
                    searchText?.visibility = View.VISIBLE
                    setFocus()
                    startSearch = false
                    startTitleText?.visibility = View.GONE
                    rightIcon?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.search_cancel))
                }else{
                    clearSearch()
                }

                searchText?.onChange {
                    if(it.length > 2){
                        callbackListener?.onSearch(it)
                    }else if(it.isEmpty()){
                        clearSearch()
                    }
                }
            }

        }
    }

    interface Callback {
        fun onBackButtonClick() {}
        fun onSearch(searchString: String) {}
        fun onClearSearch() {}
    }

    private fun setFocus(){
        searchText?.isFocusableInTouchMode = true
        searchText?.isFocusable = true
        searchText?.requestFocus()
        Helper.showKeyboard(context)
    }

    private fun clearSearch(){
        startSearch = true
        searchText?.text?.clear()
        searchText?.clearFocus()
        startTitleText?.visibility = View.VISIBLE
        searchText?.visibility = View.GONE
        rightIcon?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.search))
        callbackListener?.onClearSearch()
        Helper.hideKeyboard(context, rightIcon?.rootView)
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }


}