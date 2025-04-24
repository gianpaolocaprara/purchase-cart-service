package com.gianpaolo.caprara.purchase.cart.domain.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Order

interface OrderRepository {
    fun save(order: Order) : Order
}