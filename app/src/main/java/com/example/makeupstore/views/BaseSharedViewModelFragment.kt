package com.example.makeupstore.views

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.makeupstore.viewmodels.BaseViewModel
import kotlin.reflect.KClass

abstract class BaseSharedViewModelFragment<B:ViewDataBinding,T: BaseViewModel>(var clazz: KClass<T>):BaseFragment<B>() {
    protected lateinit var sharedViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelProvider = ViewModelProvider(requireActivity())
        sharedViewModel = viewModelProvider[clazz.java]
        super.onCreate(savedInstanceState)
    }
}