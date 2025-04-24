package com.gianpaolo.caprara.purchase.cart.infrastructure.adapters

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepositoryAdapter
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.ProductRepository
import kotlin.jvm.optionals.getOrNull

class ProductRepositoryAdapterImpl(
    private val repository: ProductRepository
) : ProductRepositoryAdapter {
    override fun findById(id: Int): Product {
        return repository
            .findById(id)
            .getOrNull() ?: throw DataNotFoundException("Product with id $id not found")
    }
}