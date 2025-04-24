package com.gianpaolo.caprara.purchase.cart.domain.models

open class Product(
    open val id: String,
    open val name: String? = null,
    open val price: Double? = null,
    open val vat: Double? = null
)