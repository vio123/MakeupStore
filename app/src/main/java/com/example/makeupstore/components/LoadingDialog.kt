package com.example.makeupstore.components

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.makeupstore.R
import com.example.makeupstore.databinding.DialogLoadingBinding

class LoadingDialog {
    private var dialog: Dialog? = null
    fun initDialog(context: Context,loadingText:String){
        val dialogBinding: DialogLoadingBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        dialog = Dialog(context)
        dialog?.setContentView(dialogBinding.root)
        dialogBinding.loadingText = loadingText
        dialog?.setCancelable(false)
        dialog?.show()
    }
    fun dismiss(){
        dialog?.dismiss()
    }
}