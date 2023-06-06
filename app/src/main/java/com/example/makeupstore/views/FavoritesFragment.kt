package com.example.makeupstore.views

import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.CartProductAdapter
import com.example.makeupstore.adapters.FavProductAdapter
import com.example.makeupstore.databinding.FragmentFavoritesBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener
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
            favProductAdapter = FavProductAdapter(it,this)
            binding.rvFav.apply {
                adapter = favProductAdapter
                layoutManager = manager
            }
        }
        super.onStart()
    }

    override fun onItemClick(position: Int) {

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