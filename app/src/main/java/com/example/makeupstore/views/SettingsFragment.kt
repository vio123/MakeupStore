package com.example.makeupstore.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FragmentSettingsBinding
import com.example.makeupstore.models.User
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.viewmodels.SettingsViewModel

class SettingsFragment :
    BaseSharedViewModelFragment<FragmentSettingsBinding, SettingsViewModel>(SettingsViewModel::class) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    private lateinit var sharedPreferences: SharedPreferences
    private var user: User? = null

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onStart() {
        super.onStart()
        binding.viewModel = sharedViewModel
        sharedViewModel.getUserData(UserUtils.getUserId()).observe(this) {
            user = it
            binding.user = user
        }
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        // Verificați tema stocată anterior și setați comutatorul în consecință
        val savedTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_NO)
        binding.changeTheme.isChecked = (savedTheme == AppCompatDelegate.MODE_NIGHT_YES)
        binding.changeTheme.setOnCheckedChangeListener { _, isChecked ->
            val nightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            setThemeAndRecreate(nightMode)
            saveThemeToSharedPreferences(nightMode)
        }
        var isIcon1SelectedNume = true
        var isIcon1SelectedPrenume = true
        var isIcon1SelectedEmail = true
        var isIcon1SelectedTelefon = true
        var isIcon1SelectedAdresa = true
        binding.etAdresa.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd =
                    binding.etAdresa.compoundDrawablesRelative[2] // Get the end drawable

                if (drawableEnd != null && event.rawX >= binding.etAdresa.right - binding.etAdresa.compoundPaddingEnd - drawableEnd.bounds.width()) {
                    if (isIcon1SelectedAdresa) {
                        binding.etAdresa.isFocusable = true
                        binding.etAdresa.isFocusableInTouchMode = true
                        binding.etAdresa.isClickable = true
                        binding.etAdresa.requestFocus()
                        binding.etAdresa.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_home),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check),
                            null
                        )
                        isIcon1SelectedAdresa = false
                    } else {
                        val updatedUser = user?.copyWith(adresa = binding.etAdresa.text.toString())
                        sharedViewModel.updatetUserData(updatedUser)
                        binding.etAdresa.isFocusable = false
                        binding.etAdresa.isClickable = false
                        binding.etAdresa.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_home),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_edit),
                            null
                        )
                        isIcon1SelectedAdresa = true
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        binding.etName.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd =
                    binding.etName.compoundDrawablesRelative[2] // Get the end drawable

                if (drawableEnd != null && event.rawX >= binding.etName.right - binding.etName.compoundPaddingEnd - drawableEnd.bounds.width()) {
                    if (isIcon1SelectedNume) {
                        binding.etName.isFocusable = true
                        binding.etName.isFocusableInTouchMode = true
                        binding.etName.isClickable = true
                        binding.etName.requestFocus()
                        binding.etName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_person),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check),
                            null
                        )
                        isIcon1SelectedNume = false
                    } else {
                        val updatedUser = user?.copyWith(nume = binding.etName.text.toString())
                        sharedViewModel.updatetUserData(updatedUser)
                        binding.etName.isFocusable = false
                        binding.etName.isClickable = false
                        binding.etName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_person),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_edit),
                            null
                        )
                        isIcon1SelectedNume = true
                    }
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.etPrenume.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd =
                    binding.etPrenume.compoundDrawablesRelative[2] // Get the end drawable

                if (drawableEnd != null && event.rawX >= binding.etPrenume.right - binding.etPrenume.compoundPaddingEnd - drawableEnd.bounds.width()) {
                    if (isIcon1SelectedPrenume) {
                        binding.etPrenume.isFocusable = true
                        binding.etPrenume.isFocusableInTouchMode = true
                        binding.etPrenume.isClickable = true
                        binding.etPrenume.requestFocus()
                        binding.etPrenume.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_person),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check),
                            null
                        )
                        isIcon1SelectedPrenume = false
                    } else {
                        val updatedUser =
                            user?.copyWith(prenume = binding.etPrenume.text.toString())
                        sharedViewModel.updatetUserData(updatedUser)
                        binding.etPrenume.isFocusable = false
                        binding.etPrenume.isClickable = false
                        binding.etPrenume.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_person),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_edit),
                            null
                        )
                        isIcon1SelectedPrenume = true
                    }
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.etEmail.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd =
                    binding.etEmail.compoundDrawablesRelative[2] // Get the end drawable

                if (drawableEnd != null && event.rawX >= binding.etEmail.right - binding.etEmail.compoundPaddingEnd - drawableEnd.bounds.width()) {
                    if (isIcon1SelectedEmail) {
                        binding.etEmail.isFocusable = true
                        binding.etEmail.isFocusableInTouchMode = true
                        binding.etEmail.isClickable = true
                        binding.etEmail.requestFocus()
                        binding.etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_email),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check),
                            null
                        )
                        isIcon1SelectedEmail = false
                    } else {
                        val updatedUser = user?.copyWith(email = binding.etEmail.text.toString())
                        sharedViewModel.updatetUserData(updatedUser)
                        binding.etEmail.isFocusable = false
                        binding.etEmail.isClickable = false
                        binding.etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_email),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_edit),
                            null
                        )
                        isIcon1SelectedEmail = true
                    }
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.etPhone.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd =
                    binding.etPhone.compoundDrawablesRelative[2] // Get the end drawable

                if (drawableEnd != null && event.rawX >= binding.etPhone.right - binding.etPhone.compoundPaddingEnd - drawableEnd.bounds.width()) {
                    if (isIcon1SelectedTelefon) {
                        binding.etPhone.isFocusable = true
                        binding.etPhone.isFocusableInTouchMode = true
                        binding.etPhone.isClickable = true
                        binding.etPhone.requestFocus()
                        binding.etPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_phone),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check),
                            null
                        )
                        isIcon1SelectedTelefon = false
                    } else {
                        val updatedUser = user?.copyWith(contact = binding.etPhone.text.toString())
                        sharedViewModel.updatetUserData(updatedUser)
                        binding.etPhone.isFocusable = false
                        binding.etPhone.isClickable = false
                        binding.etPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_phone),
                            null,
                            // Set the new drawable
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_edit),
                            null
                        )
                        isIcon1SelectedTelefon = true
                    }
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }
    }


    private fun saveThemeToSharedPreferences(theme: Int) {
        sharedPreferences.edit().putInt("theme", theme).apply()
    }

    private fun setThemeAndRecreate(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        // Re-încărcați fragmentul curent pentru a aplica tema actualizată
        parentFragmentManager.beginTransaction()
            .detach(this)
            .attach(this)
            .commit()
    }
}