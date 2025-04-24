package com.gianpaolo.caprara.purchase.cart.domain.models

open class Product(
    open val id: String,
    open val name: String,
    open val price: Double,
    open val vat: Double
)