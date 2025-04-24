package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.models.Order

class CreateOrderUseCase {
    fun apply(order: Order): Order{
        return Order(id = 111, items = order.items)
    }
}