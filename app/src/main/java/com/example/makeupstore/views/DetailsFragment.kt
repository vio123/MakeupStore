package com.example.makeupstore.views


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.makeupstore.R
import com.example.makeupstore.components.AlertDialog
import com.example.makeupstore.databinding.FragmentDetailsBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.ProductUtils
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.viewmodels.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailsFragment :
    BaseSharedViewModelFragment<FragmentDetailsBinding, DetailsViewModel>(DetailsViewModel::class) {
    private var data: Bundle? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = arguments
        binding.productDescription.text = data?.getString(ProductUtils.PROD_DESCRIPTION)
        Glide.with(binding.root)
            .load(data?.getString(ProductUtils.PROD_IMAGE))
            .placeholder(R.drawable.placeholder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.productImg)
        sharedViewModel.viewModelScope.launch {
            if(sharedViewModel.isFav(data?.getString(ProductUtils.PROD_NAME).toString())){
                binding.favBtn.setImageDrawable(ContextCompat.getDrawable(binding.root.context,R.drawable.ic_fav))
            }else{
                binding.favBtn.setImageDrawable(ContextCompat.getDrawable(binding.root.context,R.drawable.ic_fav_off))
            }
        }
        binding.productName.text = data?.getString(ProductUtils.PROD_NAME)
        binding.favBtn.setOnClickListener {
            if (UserUtils.isUserGuest()) {
                context?.let { ctx ->
                    AlertDialog.showAlertDialog(
                        ctx,
                        title = getString(R.string.logged_as_guest_title),
                        getString(R.string.logged_as_guest_message),
                        positiveBtnText = getString(R.string.dialog_positive_btn),
                        negativeBtnText = getString(R.string.dialog_negative_btn)
                    ) {
                        val startNavController =
                            requireActivity().findNavController(R.id.fragment)
                        startNavController.navigate(R.id.action_startFragment_to_loginScreenFragment)
                    }
                }
            }else{
                sharedViewModel.viewModelScope.launch {
                    if(sharedViewModel.isFav(data?.getString(ProductUtils.PROD_NAME).toString())){
                        binding.favBtn.setImageDrawable(ContextCompat.getDrawable(binding.root.context,R.drawable.ic_fav_off))
                    }else{
                        binding.favBtn.setImageDrawable(ContextCompat.getDrawable(binding.root.context,R.drawable.ic_fav))
                    }
                    sharedViewModel.addToFav(favProduct = FavProduct(
                        idUser = UserUtils.getUserId(),
                        name = data?.getString(ProductUtils.PROD_NAME),
                        price = data?.getString(ProductUtils.PROD_PRICE).toString(),
                        image_link = data?.getString(ProductUtils.PROD_IMAGE),
                        description = data?.getString(ProductUtils.PROD_DESCRIPTION)
                    ))
                }
            }
        }
        binding.addToCartBtn.setOnClickListener {
            if (UserUtils.isUserGuest()) {
                context?.let { ctx ->
                    AlertDialog.showAlertDialog(
                        ctx,
                        title = getString(R.string.logged_as_guest_title),
                        getString(R.string.logged_as_guest_message),
                        positiveBtnText = getString(R.string.dialog_positive_btn),
                        negativeBtnText = getString(R.string.dialog_negative_btn)
                    ) {
                        val startNavController =
                            requireActivity().findNavController(R.id.fragment)
                        startNavController.navigate(R.id.action_startFragment_to_loginScreenFragment)
                    }
                }
            } else {
                sharedViewModel.viewModelScope.launch {
                    val insertedId = sharedViewModel.addToCart(
                        CartProduct(
                            name = data?.getString(ProductUtils.PROD_NAME),
                            price = data?.getString(ProductUtils.PROD_PRICE).toString(),
                            image_link = data?.getString(ProductUtils.PROD_IMAGE),
                            description = data?.getString(ProductUtils.PROD_DESCRIPTION),
                            quantity = 1,
                            idUser = UserUtils.getUserId()
                        )
                    )
                    if (insertedId != -1L) {
                       Snackbar.make(binding.root,getString(R.string.added_to_cart),Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}