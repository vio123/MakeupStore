package com.example.makeupstore.utils

import androidx.lifecycle.MutableLiveData
import com.stripe.android.paymentsheet.PaymentSheet

object StripeUtils {
    const val PUBLISH_KEY = ""
    const val PRIVATE_KEY = ""

    lateinit var paymentSheet: PaymentSheet

    val paymentResult:MutableLiveData<Boolean> = MutableLiveData()
}