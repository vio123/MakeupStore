package com.example.makeupstore.views


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.CardCategoryAdapter
import com.example.makeupstore.adapters.CardProductAdapter
import com.example.makeupstore.components.AlertDialog
import com.example.makeupstore.databinding.FragmentHomeBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.utils.ProductUtils
import com.example.makeupstore.utils.UserUtils
import com.example.makeupstore.viewmodels.HomeViewModel

class HomeFragment :
    BaseSharedViewModelFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private lateinit var managerGrid: RecyclerView.LayoutManager
    private lateinit var manager: RecyclerView.LayoutManager

    private lateinit var cardProductAdapter: CardProductAdapter
    private var poz: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        binding.sharedViewModel = sharedViewModel
        sharedViewModel.getAllProducts()
    }

    override fun onStart() {
        super.onStart()
        managerGrid = GridLayoutManager(binding.root.context, 2)
        manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        sharedViewModel.map.observe(this) {
            sharedViewModel.getJustForYou()
        }
        sharedViewModel.categories.observe(this) {
            binding.rvCategories.apply {
                adapter = CardCategoryAdapter(it, object : OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        if (poz != position) {
                            poz = position
                            sharedViewModel.getProductsByCategory(category = it[position].title)
                        } else {
                            poz = -1
                            sharedViewModel.getJustForYou()
                        }
                    }

                    override fun onItemDeleteCart(cartProduct: CartProduct) {

                    }

                    override fun onUpdateQuantity(quantity: Int, prodId: Int) {

                    }

                    override fun onItemDeleteFav(favProduct: FavProduct) {

                    }

                })
                layoutManager = manager
            }
        }
        sharedViewModel.products.observe(this) {
            sharedViewModel.categoryText.set(getString(R.string.just_for_you))
            cardProductAdapter = CardProductAdapter(it, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putString(ProductUtils.PROD_NAME,it[position].name)
                    bundle.putString(ProductUtils.PROD_DESCRIPTION,it[position].description)
                    bundle.putString(ProductUtils.PROD_IMAGE,it[position].image_link)
                    bundle.putString(ProductUtils.PROD_PRICE,it[position].price)
                    findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args = bundle)
                }

                override fun onItemDeleteCart(cartProduct: CartProduct) {

                }

                override fun onUpdateQuantity(quantity: Int, prodId: Int) {

                }

                override fun onItemDeleteFav(favProduct: FavProduct) {

                }

            })
            binding.rvItems.apply {
                adapter = cardProductAdapter
                layoutManager = managerGrid
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView? = menuItem.actionView as? SearchView
        searchView?.queryHint = getString(R.string.search)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cardProductAdapter.filter.filter(newText)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                if(UserUtils.isUserGuest()) {
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
                    findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
                }
            }
            R.id.action_fav -> {
                if(UserUtils.isUserGuest()) {
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
                    findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }
}