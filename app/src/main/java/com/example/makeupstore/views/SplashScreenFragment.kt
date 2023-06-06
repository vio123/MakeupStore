package com.example.makeupstore.views

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FragmentSplashScreenBinding
import com.example.makeupstore.utils.UserUtils

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_splash_screen
    }

    override fun onStart() {
        super.onStart()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (UserUtils.isAlreadyLogged()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_startFragment)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginScreenFragment)
            }
        }, 2000)
    }
}