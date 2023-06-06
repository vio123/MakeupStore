package com.example.makeupstore.views

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.ProfileProductAdapter
import com.example.makeupstore.components.AlertDialog
import com.example.makeupstore.databinding.FragmentProfileBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.utils.ProductUtils
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
            if (it != null) {
                profileProductAdapter = ProfileProductAdapter(it, object : OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val bundle = Bundle()
                        bundle.putString(ProductUtils.PROD_NAME, it[position]?.name)
                        bundle.putString(ProductUtils.PROD_DESCRIPTION, it[position]?.description)
                        bundle.putString(ProductUtils.PROD_IMAGE, it[position]?.image_link)
                        bundle.putString(ProductUtils.PROD_PRICE, it[position]?.price)
                        findNavController().navigate(R.id.action_profileFragment_to_detailsFragment, args = bundle)
                    }

                    override fun onItemDeleteCart(cartProduct: CartProduct) {

                    }

                    override fun onUpdateQuantity(quantity: Int, prodId: Int) {

                    }

                    override fun onItemDeleteFav(favProduct: FavProduct) {

                    }

                })
                binding.rvProduct.apply {
                    adapter = profileProductAdapter
                    layoutManager = manager
                }
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