package com.gianpaolo.caprara.purchase.cart.domain.models

data class ProductOrder(
    override val id: String,
    override val name: String,
    override val price: Double,
    override val vat: Double,
    val quantity: Int
) : Product(
    id = id,
    name = name,
    price = price,
    vat = vat
)