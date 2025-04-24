package com.gianpaolo.caprara.purchase.cart.domain.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Product

interface ProductRepositoryAdapter {
    fun findById(id: Int): Product
}