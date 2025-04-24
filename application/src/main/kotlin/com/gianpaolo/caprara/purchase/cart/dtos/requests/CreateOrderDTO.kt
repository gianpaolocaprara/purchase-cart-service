package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.ProductOrder

class CreateOrderDTO(
    val order: OrderDTO
)

fun CreateOrderDTO.toModel(): Order = Order(
    items = order.items.map {
        ProductOrder(
            id = it.productId,
            quantity = it.quantity
        )
    }
)