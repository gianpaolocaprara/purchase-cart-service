package com.gianpaolo.caprara.purchase.cart.infrastructure.adapters

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepositoryAdapter
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.OrderRepository

class OrderRepositoryAdapterImpl(
    private val repository: OrderRepository
) : OrderRepositoryAdapter {
    override fun save(order: Order): Order {
        return this.repository.save(order)
    }

}