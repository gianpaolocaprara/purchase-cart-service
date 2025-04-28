package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

class OrderItemDTO(
    @Schema(description = "The product id", example = "1")
    @field:JsonProperty("product_id")
    val productId: Int,
    @Schema(description = "The quantity (cannot be negative or zero)", example = "3")
    val quantity: Int
)