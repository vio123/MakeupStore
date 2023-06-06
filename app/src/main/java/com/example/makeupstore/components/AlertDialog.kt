package com.example.makeupstore.components

import android.app.AlertDialog
import android.content.Context

object AlertDialog {
    fun showAlertDialog(context: Context, title:String, message:String,positiveBtnText:String="Ok",negativeBtnText:String = "Cancel",okClick: () -> Unit) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton(positiveBtnText) { dialog, which ->
            okClick()
        }

        alertDialog.setNegativeButton(negativeBtnText) { dialog, which ->

        }

        alertDialog.show()
    }
}