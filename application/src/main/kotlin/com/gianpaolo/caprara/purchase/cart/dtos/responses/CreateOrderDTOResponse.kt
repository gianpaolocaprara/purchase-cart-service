package com.gianpaolo.caprara.purchase.cart.dtos.responses

import com.fasterxml.jackson.annotation.JsonProperty

class CreateOrderDTOResponse(
    @JsonProperty("order_id")
    val id: Int,
    @JsonProperty("order_price")
    val price: Double,
    @JsonProperty("order_vat")
    val vat: Double,
    val items: List<ProductOrderDTOResponse>
)