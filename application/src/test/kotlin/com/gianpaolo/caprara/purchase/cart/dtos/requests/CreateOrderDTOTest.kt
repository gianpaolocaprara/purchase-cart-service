package com.gianpaolo.caprara.purchase.cart.dtos.requests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateOrderDTOTest {

    @Test
    fun `create order dto expected values`() {
        val createOrderDTO = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    ProductDTO("1", 1),
                    ProductDTO("2", 2)
                )
            )
        )

        assertEquals(createOrderDTO.order.items.size, 2)
        assertEquals(createOrderDTO.order.items[0].productId, "1")
        assertEquals(createOrderDTO.order.items[0].quantity, 1)
        assertEquals(createOrderDTO.order.items[1].productId, "2")
        assertEquals(createOrderDTO.order.items[1].quantity, 2)
    }
}