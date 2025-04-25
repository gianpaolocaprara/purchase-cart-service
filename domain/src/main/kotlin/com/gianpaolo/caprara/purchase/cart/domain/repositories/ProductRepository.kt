package com.gianpaolo.caprara.purchase.cart.domain.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Product

interface ProductRepository {
    fun save(product: Product)
    fun findById(id: Int): Product
}