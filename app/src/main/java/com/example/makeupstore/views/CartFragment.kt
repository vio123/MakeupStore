package com.example.makeupstore.views


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.makeupstore.R
import com.example.makeupstore.adapters.CartProductAdapter
import com.example.makeupstore.database.AppDatabase
import com.example.makeupstore.databinding.FragmentCartBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.repository.CartRepository
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.utils.ProductUtils
import com.example.makeupstore.viewmodels.CartViewModel
import kotlinx.coroutines.launch

class CartFragment : BaseSharedViewModelFragment<FragmentCartBinding, CartViewModel>(
    CartViewModel::class
), OnItemClickListener {

    private lateinit var manager: RecyclerView.LayoutManager
    private var cartProductAdapter: CartProductAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        sharedViewModel.allCart.observe(this) {
            cartProductAdapter = CartProductAdapter(it, this)
            binding.rvCart.apply {
                adapter = cartProductAdapter
                layoutManager = manager
            }
            binding.checkOutBtn.isEnabled = it.isNotEmpty()
        }
        binding.checkOutBtn.setOnClickListener {
            sharedViewModel.viewModelScope.launch {
                if (sharedViewModel.checkout() != -1) {
                    findNavController().navigate(R.id.action_cartFragment_to_homeFragment)
                }
            }
        }
        super.onStart()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putString(ProductUtils.PROD_NAME, sharedViewModel.allCart.value?.get(position)?.name)
        bundle.putString(
            ProductUtils.PROD_DESCRIPTION,
            sharedViewModel.allCart.value?.get(position)?.description
        )
        bundle.putString(
            ProductUtils.PROD_IMAGE,
            sharedViewModel.allCart.value?.get(position)?.image_link
        )
        bundle.putString(
            ProductUtils.PROD_PRICE,
            sharedViewModel.allCart.value?.get(position)?.price
        )
        findNavController().navigate(R.id.action_cartFragment_to_detailsFragment, args = bundle)
    }

    override fun onItemDeleteCart(cartProduct: CartProduct) {
        sharedViewModel.delete(cartProduct)
    }

    override fun onUpdateQuantity(quantity: Int, prodId: Int) {
        sharedViewModel.viewModelScope.launch {
            sharedViewModel.updateQuantity(quantity, prodId)
        }
    }

    override fun onItemDeleteFav(favProduct: FavProduct) {

    }
}