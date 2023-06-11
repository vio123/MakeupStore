package com.example.makeupstore.utils

import androidx.lifecycle.MutableLiveData
import com.stripe.android.paymentsheet.PaymentSheet

object StripeUtils {
    const val PUBLISH_KEY = "pk_test_51MonMNAhhBRFGi9lHQfzlb0fwEczxpTbT6DxZsGyO9Km1rw7Rg1QQDGqTY6IgTzKYhJKJIoY12Rwg8yqyQXrnXQc00qQgNVOom"
    const val PRIVATE_KEY = "sk_test_51MonMNAhhBRFGi9lFLH3BT2yQbe0ZAuW3OfUOrcIoqyq0O0FOzuc2P9nqPwDBipd3XGWKEhPxjiuMCI7h0gakHS100qP5Pvz74"

    lateinit var paymentSheet: PaymentSheet

    val paymentResult:MutableLiveData<Boolean> = MutableLiveData()
}