
package com.tmdb

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_SETTINGS
import android.provider.Settings.ACTION_WIRELESS_SETTINGS
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import com.afollestad.materialdialogs.MaterialDialog
import com.tmdb.app.R
import com.tmdb.app.interfaces.OnDialogButton
import com.google.android.material.snackbar.Snackbar


object AppAlerts {

	private var snackBar: Snackbar? = null
	private var statusDialog: Dialog? = null

	fun showSnackBarException(view: View?) {
		showErrorSnackBar(view, view?.context?.getString(R.string.general_exception))
	}

	fun showSnackBarError(view: View?, message: String?, title: String? = null) {
		showErrorSnackBar(view, message, title)
	}


	private fun showErrorSnackBar(view: View?, message: String?, title: String? = null) {
		hideSnackBar()
		view?.let {

			snackBar = Snackbar.make(it, "", Snackbar.LENGTH_INDEFINITE)
			val snackBarLayout = snackBar?.view as? Snackbar.SnackbarLayout
			snackBarLayout?.setBackgroundColor(ContextCompat.getColor(it.context, android.R.color.transparent))
			val textView = snackBarLayout?.findViewById(com.google.android.material.R.id.snackbar_text) as? TextView
			textView?.visibility = INVISIBLE
			val mInflater = view.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
			val snakeView = mInflater?.inflate(R.layout.snackbar_layout, null)

			snakeView?.setOnClickListener { snackBar?.dismiss() }

			val titleView = snakeView?.findViewById<TextView>(R.id.titleTextView)
			val messageView = snakeView?.findViewById<TextView>(R.id.msgTextView)
			titleView?.visibility = GONE
			title?.let {
				titleView?.text = title
				titleView?.visibility = VISIBLE
			}
			view.setOnClickListener { snackBar?.dismiss() }
			messageView?.text = message

			//If the view is not covering the whole snackbar layout, add this line
			snackBarLayout?.setPadding(0, 0, 0, 0)
			snackBarLayout?.addView(snakeView, 0)

			if (!message.isNullOrEmpty())
				snackBar?.show()
		}


	}

	fun showResponseDialog(context: Context?, title: String? = null,
						   msg: String? = "", onDialogButton: OnDialogButton? = null,
						   positiveText: Int = R.string.exit,
						   isCancelable: Boolean = false, negativeText: Int? = null) {

		val context = context ?: return

		val builder = MaterialDialog.Builder(context)
				.content(msg.toString())
				.positiveText(positiveText)
				.onNegative { dialog, which ->
					onDialogButton?.onCancel()
					dialog.dismiss()
				}
				.onPositive { dialog, which -> onDialogButton?.onOk() }
		if (title != null) {
			builder.title(title)
		}

		if (negativeText != null) builder.negativeText(negativeText)

		builder.cancelable(isCancelable)
		builder.typeface(ResourcesCompat.getFont(context, R.font.tilliumweb_bold), ResourcesCompat.getFont(context, R.font.titilliumweb_light))
		builder.show()
    }

	fun hideSnackBar(){
		snackBar?.dismiss()
		snackBar = null
	}


}