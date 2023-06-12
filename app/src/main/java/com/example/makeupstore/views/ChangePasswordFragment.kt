package com.example.makeupstore.views


import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FragmentChangePasswordBinding
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.utils.Validators
import com.example.makeupstore.viewmodels.ChangePasswordViewModel
import com.google.android.material.snackbar.Snackbar

class ChangePasswordFragment :
    BaseSharedViewModelFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>(
        ChangePasswordViewModel::class
    ) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_change_password
    }

    override fun onStart() {
        super.onStart()
        binding.etOldParola.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.pass.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (Validators.isValidPassword(sharedViewModel.pass.value)) {
                    binding.etOldParola.error = null
                } else {
                    binding.etOldParola.error = "Invalid password"
                }
            }
        })

        binding.btnChangePassword.setOnClickListener {
            sharedViewModel.changePassword(
                email = UserUtils.getUserEmail(),
                oldPassword = binding.etOldParola.text.toString(),
                newPassword = binding.etNewParola.text.toString()
            )
        }
        sharedViewModel.successOrFailed.observe(this) {
            if (it) {
                findNavController().navigate(R.id.action_changePasswordFragment_to_settingsFragment)
            } else {
                Snackbar.make(binding.root, "Parola nu a putut fi schimbata", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}