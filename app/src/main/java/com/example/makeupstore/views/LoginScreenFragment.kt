package com.example.makeupstore.views


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.makeupstore.components.LoadingDialog
import com.example.makeupstore.databinding.FragmentLoginScreenBinding
import com.example.makeupstore.utils.Validators.Companion.isValidEmail
import com.example.makeupstore.utils.Validators.Companion.isValidPassword
import com.example.makeupstore.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.example.makeupstore.R

class LoginScreenFragment : BaseSharedViewModelFragment<FragmentLoginScreenBinding,LoginViewModel>(LoginViewModel::class) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_login_screen
    }
    private var loadingDialog:LoadingDialog? = null
    override fun onStart() {
        super.onStart()
        loadingDialog = LoadingDialog()
        binding.sharedViewModel = sharedViewModel
        val loginView = binding.loginView
        loginView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
            override fun onLayoutChange(
                p0: View?,
                p1: Int,
                p2: Int,
                p3: Int,
                p4: Int,
                p5: Int,
                p6: Int,
                p7: Int,
                p8: Int,
            ) {
                loginView.removeOnLayoutChangeListener(this)
                // Start the animation
                val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                loginView.startAnimation(animation)
            }

        })
        binding.sharedViewModel = sharedViewModel
        binding.etEmail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.email.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if(isValidEmail(sharedViewModel.email.value.toString())){
                    binding.etEmail.error = null
                }else{
                    binding.etEmail.error = "Invalid email"
                }
            }

        })
        sharedViewModel.loading.observe(this){
            if(isValidPassword(sharedViewModel.pass.value)){
                binding.etPass.error = null
            }else{
                binding.etPass.error = "Invalid password"
            }
            if(isValidEmail(sharedViewModel.email.value.toString())){
                binding.etEmail.error = null
            }else{
                binding.etEmail.error = "Invalid email"
            }
            if(it == true){
                loadingDialog?.initDialog(binding.root.context,"Logging in")
            }else{
                loadingDialog?.dismiss()
            }
        }
        binding.etPass.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.pass.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if(isValidPassword(sharedViewModel.pass.value)){
                    binding.etPass.error = null
                }else{
                    binding.etPass.error = "Invalid password"
                }
            }

        })
        sharedViewModel.success.observe(this){
            if(it.isNotEmpty()){
                sharedViewModel.clearSuccess()
                sharedViewModel.clearLoading()
                sharedViewModel.clearError()
                findNavController().navigate(R.id.action_loginScreenFragment_to_startFragment)
            }
        }
        sharedViewModel.error.observe(this){
            if(it.isNotEmpty()) {
                sharedViewModel.clearError()
                sharedViewModel.clearLoading()
                Snackbar.make(binding.root, "email or password aren't incorrect", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.createAccount.setOnClickListener {
            sharedViewModel.clearError()
            findNavController().navigate(R.id.action_loginScreenFragment_to_registerScreenFragment)
        }
    }
}