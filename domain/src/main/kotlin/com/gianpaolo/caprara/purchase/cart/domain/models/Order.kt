package com.gianpaolo.caprara.purchase.cart.domain.models

data class Order(
    val id: Int? = null,
    val items: List<OrderItem>,
    val price: Double? = null,
    val vat: Double? = null
)