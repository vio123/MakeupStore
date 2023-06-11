package com.example.makeupstore.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }
}