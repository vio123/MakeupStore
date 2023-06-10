package com.example.makeupstore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.makeupstore.utils.StripeUtils
import com.example.makeupstore.utils.StripeUtils.paymentSheet
import com.google.android.material.snackbar.Snackbar
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        paymentSheet = PaymentSheet(this) { paymentSheetResult: PaymentSheetResult? ->
            onPaymentResult(paymentSheetResult)
        }
    }

    private fun onPaymentResult(paymentSheetResult: PaymentSheetResult?) {
        if (paymentSheetResult is PaymentSheetResult.Completed) {
            val rootView = findViewById<View>(android.R.id.content)
            val snackbar = Snackbar.make(rootView, "Payment completed", Snackbar.LENGTH_SHORT)
            snackbar.show()
            StripeUtils.paymentResult.value = true
        } else {
            StripeUtils.paymentResult.value = false
        }
    }
}