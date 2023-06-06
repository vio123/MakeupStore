package com.example.makeupstore.views

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.CartProductAdapter
import com.example.makeupstore.adapters.FavProductAdapter
import com.example.makeupstore.databinding.FragmentFavoritesBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.utils.ProductUtils
import com.example.makeupstore.viewmodels.FavoritesViewModel
import kotlinx.coroutines.launch

class FavoritesFragment :
    BaseSharedViewModelFragment<FragmentFavoritesBinding, FavoritesViewModel>(FavoritesViewModel::class),
    OnItemClickListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_favorites
    }

    private lateinit var manager: RecyclerView.LayoutManager
    private var favProductAdapter: FavProductAdapter? = null

    override fun onStart() {
        manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        sharedViewModel.allFav.observe(this) {
            favProductAdapter = FavProductAdapter(it, this)
            binding.rvFav.apply {
                adapter = favProductAdapter
                layoutManager = manager
            }
        }
        super.onStart()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putString(ProductUtils.PROD_NAME, sharedViewModel.allFav.value?.get(position)?.name)
        bundle.putString(
            ProductUtils.PROD_DESCRIPTION,
            sharedViewModel.allFav.value?.get(position)?.description
        )
        bundle.putString(
            ProductUtils.PROD_IMAGE,
            sharedViewModel.allFav.value?.get(position)?.image_link
        )
        bundle.putString(
            ProductUtils.PROD_PRICE,
            sharedViewModel.allFav.value?.get(position)?.price
        )
        findNavController().navigate(
            R.id.action_favoritesFragment_to_detailsFragment,
            args = bundle
        )
    }

    override fun onItemDeleteCart(cartProduct: CartProduct) {

    }

    override fun onUpdateQuantity(quantity: Int, prodId: Int) {

    }

    override fun onItemDeleteFav(favProduct: FavProduct) {
        sharedViewModel.viewModelScope.launch {
            sharedViewModel.delete(favProduct)
        }
    }
}