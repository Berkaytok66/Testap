package com.berkaytok66.testapp.Companent

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.berkaytok66.testapp.R

class CustomAlertDialogCompanent(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    positiveButtonText: String,
    negativeButtonText: String,
    positiveButtonListener: DialogInterface.OnClickListener?,
    negativeButtonListener: DialogInterface.OnClickListener?
) {
    private val dialog: AlertDialog


    init{

        // Inflater and custom title layout
        val inflater = LayoutInflater.from(context)
        val customTitleView = inflater.inflate(R.layout.custom_dialog_title, null)
        customTitleView.findViewById<TextView>(R.id.title).text = title
        customTitleView.findViewById<AppCompatImageView>(R.id.icon).setImageResource(icon)


        // Building the AlertDialog
        dialog = AlertDialog.Builder(context).apply {
            setCustomTitle(customTitleView)
            setMessage(message)
            setPositiveButton(positiveButtonText, positiveButtonListener)
            setNegativeButton(negativeButtonText, negativeButtonListener)
        }.create()
    }
    fun show() {
        dialog.show()
    }

}