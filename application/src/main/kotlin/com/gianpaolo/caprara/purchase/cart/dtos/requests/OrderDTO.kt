package com.gianpaolo.caprara.purchase.cart.dtos.requests

import io.swagger.v3.oas.annotations.media.Schema

class OrderDTO(
    @Schema(description = "The items order")
    val items: List<OrderItemDTO>
)