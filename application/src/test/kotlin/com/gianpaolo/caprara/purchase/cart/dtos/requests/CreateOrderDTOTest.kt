package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateOrderDTOTest {

    @Test
    fun `create order dto expected values`() {
        val createOrderDTO = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 2)
                )
            )
        )

        assertEquals(createOrderDTO.order.items.size, 2)
        assertEquals(createOrderDTO.order.items[0].productId, 1)
        assertEquals(createOrderDTO.order.items[0].quantity, 1)
        assertEquals(createOrderDTO.order.items[1].productId, 2)
        assertEquals(createOrderDTO.order.items[1].quantity, 2)
    }

    @Test
    fun `create order dto to model expected values`() {
        val order: Order = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 2)
                )
            )
        ).toModel()

        assertEquals(order.items.size, 2)
        assertEquals(order.items[0].product.id, 1)
        assertEquals(order.items[0].quantity, 1)
        assertEquals(order.items[1].product.id, 2)
        assertEquals(order.items[1].quantity, 2)
    }
}