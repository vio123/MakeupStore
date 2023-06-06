package com.example.makeupstore.views

import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.ProfileProductAdapter
import com.example.makeupstore.components.AlertDialog
import com.example.makeupstore.databinding.FragmentProfileBinding
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.viewmodels.ProfileViewModel

class ProfileFragment :
    BaseSharedViewModelFragment<FragmentProfileBinding, ProfileViewModel>(ProfileViewModel::class) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    private lateinit var manager: RecyclerView.LayoutManager
    private var profileProductAdapter: ProfileProductAdapter? = null

    override fun onStart() {
        manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        sharedViewModel.getUserData(UserUtils.getUserId()).observe(this) {
            binding.user = it
        }
        sharedViewModel.getUserCheckouts(UserUtils.getUserId()).observe(this) {
            profileProductAdapter = ProfileProductAdapter(it)
            binding.rvProduct.apply {
                adapter = profileProductAdapter
                layoutManager = manager
            }
        }
        binding.btnLogout.setOnClickListener {
            context?.let { ctx ->
                AlertDialog.showAlertDialog(
                    ctx,
                    title = "Log Out",
                    "Do you want logout?",
                    positiveBtnText = "Yes",
                    negativeBtnText = "No"
                ) {
                    UserUtils.logOut()
                    val startNavController =
                        requireActivity().findNavController(R.id.fragment)
                    startNavController.navigate(R.id.action_startFragment_to_loginScreenFragment)
                }
            }
        }
        super.onStart()
    }
}