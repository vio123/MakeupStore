package com.example.makeupstore.views


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupstore.R
import com.example.makeupstore.adapters.CartProductAdapter
import com.example.makeupstore.databinding.FragmentCartBinding
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct
import com.example.makeupstore.utils.OnItemClickListener
import com.example.makeupstore.utils.ProductUtils
import com.example.makeupstore.utils.StripeUtils
import com.example.makeupstore.viewmodels.CartViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CartFragment : BaseSharedViewModelFragment<FragmentCartBinding, CartViewModel>(
    CartViewModel::class
), OnItemClickListener {

    private lateinit var manager: RecyclerView.LayoutManager
    private var cartProductAdapter: CartProductAdapter? = null

    private lateinit var customerId: String
    private lateinit var clientSecret: String
    private var totalPrice: Double? = 0.0
    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        PaymentConfiguration.init(requireContext(), StripeUtils.PUBLISH_KEY)
        // Observe the allCart LiveData
        sharedViewModel.allCart.observe(viewLifecycleOwner) { cartProducts ->
            cartProductAdapter = CartProductAdapter(cartProducts, this)
            binding.rvCart.apply {
                adapter = cartProductAdapter
                layoutManager = manager
            }
            binding.checkOutBtn.isEnabled = cartProducts.isNotEmpty()
        }


    }

    override fun onStart() {
        sharedViewModel.viewModelScope.launch {
            customerId = sharedViewModel.getCustomer()
            totalPrice = sharedViewModel.getTotalPrice()
            if(totalPrice == null){
                totalPrice = 0.0
            }
            val decimalFormat = DecimalFormat("0.00")
            val formattedTotalPrice = decimalFormat.format(totalPrice)
            val stripePrice: Int = (formattedTotalPrice.toDouble() * 100).toInt()
            clientSecret = sharedViewModel.getClientSecret(customerId, stripePrice)
        }
        binding.checkOutBtn.setOnClickListener {
            PaymentFlow()
        }
        StripeUtils.paymentResult.observe(this){
            if(it == true){
                sharedViewModel.viewModelScope.launch {
                    if (sharedViewModel.checkout() != -1) {
                        findNavController().navigate(R.id.action_cartFragment_to_homeFragment)
                    }
                }
            }
        }
        super.onStart()
    }

    private fun PaymentFlow() {
        val address = PaymentSheet.Address(country = "RO")
        val billingAddress = PaymentSheet.BillingDetails(
            address = address,
            email = "test@gmail.com"
        )
        val configuration = PaymentSheet.Configuration(
            merchantDisplayName = "Vio company",
            defaultBillingDetails = billingAddress
        )
        StripeUtils.paymentSheet.presentWithPaymentIntent(
            clientSecret,
            configuration
        )
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