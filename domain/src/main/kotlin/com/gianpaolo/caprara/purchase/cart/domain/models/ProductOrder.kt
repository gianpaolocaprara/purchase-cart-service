package com.gianpaolo.caprara.purchase.cart.domain.models

data class ProductOrder(
    override val id: String,
    override val name: String? = null,
    override val price: Double? = null,
    override val vat: Double? = null,
    val quantity: Int
) : Product(
    id = id,
    name = name,
    price = price,
    vat = vat
)