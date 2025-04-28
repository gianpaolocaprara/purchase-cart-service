package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product

class CreateOrderDTO(
    val order: OrderDTO
)

fun CreateOrderDTO.toModel(): Order = Order(
    items = order.items.map {
        OrderItem(
            product = Product(id = it.productId),
            quantity = it.quantity
        )
    }
)