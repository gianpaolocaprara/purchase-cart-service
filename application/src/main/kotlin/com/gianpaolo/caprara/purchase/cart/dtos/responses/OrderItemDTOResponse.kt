package com.gianpaolo.caprara.purchase.cart.dtos.responses

import com.fasterxml.jackson.annotation.JsonProperty

class OrderItemDTOResponse(
    @JsonProperty("product_id")
    val id: Int,
    val quantity: Int,
    val price: Double,
    val vat: Double
)