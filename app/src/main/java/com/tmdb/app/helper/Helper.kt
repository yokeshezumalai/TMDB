package com.tmdb.app.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat

object Helper {

    /**
     * Method to hide the keyboard
     */
    fun hideKeyboard(context: Context, view: View?) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    /**
     * Method to show the keyboard
     */
    fun showKeyboard(context: Context) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Method to convert DP to pixels
     */
    fun convertDPToPixels(activity: Activity?, dp: Int): Float {
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val logicalDensity = metrics.density
        return dp * logicalDensity
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(inputDateFormat: String, outputDateFormat: String, date: String): String? {
        val inputFormat = SimpleDateFormat(inputDateFormat)
        val outputFormat = SimpleDateFormat(outputDateFormat)
        val inputDate = inputFormat.parse(date)
        return outputFormat.format(inputDate)
    }

}