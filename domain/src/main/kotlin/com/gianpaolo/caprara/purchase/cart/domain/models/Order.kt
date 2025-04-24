package com.gianpaolo.caprara.purchase.cart.domain.models

data class Order(
    val id: Int,
    val items: List<ProductOrder>,
    val price: Double,
    val vat: Double
)