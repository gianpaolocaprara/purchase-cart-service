package com.gianpaolo.caprara.purchase.cart.dtos.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.gianpaolo.caprara.purchase.cart.domain.models.Order

class CreateOrderDTOResponse(
    @JsonProperty("order_id")
    val id: Int,
    @JsonProperty("order_price")
    val price: Double,
    @JsonProperty("order_vat")
    val vat: Double,
    val items: List<OrderItemDTOResponse>
)

fun Order.toCreateOrderDTOResponse(): CreateOrderDTOResponse = CreateOrderDTOResponse(
    id = this.id!!,
    items = this.items.map {
        OrderItemDTOResponse(
            id = it.product.id,
            quantity = it.quantity,
            price = it.product.price!!,
            vat = it.product.vat!!
        )
    },
    vat = this.vat!!,
    price = this.price!!
)