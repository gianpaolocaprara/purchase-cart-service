package com.gianpaolo.caprara.purchase.cart.domain.models

data class Product(
    val id: String,
    val name: String? = null,
    val price: Double? = null,
    val vat: Double? = null
)