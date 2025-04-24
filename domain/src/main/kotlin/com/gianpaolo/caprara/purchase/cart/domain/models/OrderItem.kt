package com.gianpaolo.caprara.purchase.cart.domain.models

data class OrderItem(
    val product: Product,
    val quantity: Int
)