package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

data class OrderItemEntity(
    val productId: Int,
    val price: Double,
    val vat: Double,
    val quantity: Int
)