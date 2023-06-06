package com.example.makeupstore.views

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.makeupstore.R
import com.example.makeupstore.components.LoadingDialog
import com.example.makeupstore.databinding.FragmentRegisterScreenBinding
import com.example.makeupstore.models.User
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.utils.Validators
import com.example.makeupstore.utils.Validators.Companion.isValidEmail
import com.example.makeupstore.utils.Validators.Companion.isValidPassword
import com.example.makeupstore.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterScreenFragment : BaseSharedViewModelFragment<FragmentRegisterScreenBinding,RegisterViewModel>(RegisterViewModel::class) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_register_screen
    }
    private val database = Firebase.database
    override fun onStart() {
        super.onStart()
        binding.sharedViewModel = sharedViewModel
        val registerView = binding.registerView
        registerView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
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
                registerView.removeOnLayoutChangeListener(this)
                // Start the animation
                val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                registerView.startAnimation(animation)
            }

        })
        binding.etEmail.addTextChangedListener(object: TextWatcher {
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
        binding.etParola.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.pass.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if(isValidPassword(sharedViewModel.pass.value)){
                    binding.etParola.error = null
                }else{
                    binding.etParola.error = "Invalid password"
                }
            }

        })
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    binding.etName.error = null
                }else{
                    binding.etName.error = "Invalid name"
                }
            }

        })
        binding.etPrenume.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    binding.etPrenume.error = null
                }else{
                    binding.etPrenume.error = "Invalid prenume"
                }
            }

        })
        binding.etContact.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    binding.etContact.error = null
                }else{
                    binding.etContact.error = "Invalid contact"
                }
            }

        })
        binding.etAdresa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    binding.etAdresa.error = null
                }else{
                    binding.etAdresa.error = "Invalid adresa"
                }
            }

        })
        val loadingDialog = LoadingDialog()
        sharedViewModel.loading.observe(this){
            if(isValidEmail(sharedViewModel.email.value.toString())){
                binding.etEmail.error = null
            }else{
                binding.etEmail.error = "Invalid email"
            }
            if(isValidPassword(sharedViewModel.pass.value)){
                binding.etParola.error = null
            }else{
                binding.etParola.error = "Invalid password"
            }
            if(binding.etAdresa.text.toString().isNotEmpty()){
                binding.etAdresa.error = null
            }else{
                binding.etAdresa.error = "Invalid adresa"
            }
            if(binding.etContact.text.toString().isNotEmpty()){
                binding.etContact.error = null
            }else{
                binding.etContact.error = "Invalid contact"
            }
            if(binding.etName.text.toString().isNotEmpty()){
                binding.etName.error = null
            }else{
                binding.etName.error = "Invalid nume"
            }
            if(binding.etPrenume.text.toString().isNotEmpty()){
                binding.etPrenume.error = null
            }else{
                binding.etPrenume.error = "Invalid prenume"
            }
            if(it == true){
                loadingDialog.initDialog(binding.root.context,"Register...")
            }else{
                loadingDialog.dismiss()
            }
        }
        sharedViewModel.success.observe(this){
            if(it == "registered"){
                val myRef = database.getReference("Users").child(UserUtils.getUserId())
                val user = User(
                    nume = binding.etName.text.toString(),
                    prenume = binding.etPrenume.text.toString(),
                    email = binding.etEmail.text.toString(),
                    contact = binding.etContact.text.toString(),
                    adresa = binding.etAdresa.text.toString(),
                    id = UserUtils.getUserId()
                )
                myRef.setValue(user) { error, _ ->
                    if (error == null) {
                        findNavController().navigate(R.id.action_registerScreenFragment_to_startFragment)
                    }else{
                        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                    }
                    loadingDialog.dismiss()
                }
            }
        }
    }
}