package com.example.makeupstore.views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.makeupstore.R
import com.example.makeupstore.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onStart() {
        super.onStart()
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