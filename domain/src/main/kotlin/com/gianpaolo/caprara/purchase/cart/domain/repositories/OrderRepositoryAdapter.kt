package com.gianpaolo.caprara.purchase.cart.domain.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Order

interface OrderRepositoryAdapter {
    fun save(order: Order) : Order
}