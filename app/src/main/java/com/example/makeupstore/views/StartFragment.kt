package com.example.makeupstore.views

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.makeupstore.R
import com.example.makeupstore.components.AlertDialog
import com.example.makeupstore.databinding.FragmentStartBinding
import com.example.makeupstore.utils.UserUtils

class StartFragment : BaseFragment<FragmentStartBinding>() {
    private lateinit var navController: NavController
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun getLayoutId(): Int {
        return R.layout.fragment_start
    }

    override fun onStart() {
        super.onStart()
        val upperNavHostFragment =
            childFragmentManager.findFragmentById(R.id.upperContainerView) as NavHostFragment
        navController = upperNavHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        // Configurare graful de navigare și destinatia implicita pentru containerul de fragmente deasupra BottomNavigationView
        val inflater = upperNavHostFragment.navController.navInflater
        val upperNavGraph = inflater.inflate(R.navigation.home_nav)
        upperNavHostFragment.navController.graph = upperNavGraph
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.action_profile -> {
                    if (UserUtils.isUserGuest()) {
                        context?.let { ctx ->
                            AlertDialog.showAlertDialog(
                                ctx,
                                title = "You are not logged",
                                "Do you want login?",
                                positiveBtnText = "Yes",
                                negativeBtnText = "No"
                            ) {
                                val startNavController =
                                    requireActivity().findNavController(R.id.fragment)
                                startNavController.navigate(R.id.action_startFragment_to_loginScreenFragment)
                            }
                        }
                    } else {
                        navController.navigate(R.id.profileFragment)
                    }
                    true
                }
                else -> false
            }
        }
        // Initialize onBackPressedCallback
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if the current destination is the start destination
                if (navController.currentDestination?.id == R.id.homeFragment) {
                    moveAppToBackground()
                } else {
                    // If not in the start destination, let the NavHostFragment handle the back press
                    navController.navigateUp()
                }
            }
        }
        // Add onBackPressedCallback to the activity's OnBackPressedDispatcher
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun moveAppToBackground() {
        val moveTaskToBack = Intent(Intent.ACTION_MAIN)
        moveTaskToBack.addCategory(Intent.CATEGORY_HOME)
        moveTaskToBack.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(moveTaskToBack)
    }
}