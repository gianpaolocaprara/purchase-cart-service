package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.fasterxml.jackson.annotation.JsonProperty

class ProductDTO(
    @field:JsonProperty("product_id")
    val productId: String,
    val quantity: Int
)