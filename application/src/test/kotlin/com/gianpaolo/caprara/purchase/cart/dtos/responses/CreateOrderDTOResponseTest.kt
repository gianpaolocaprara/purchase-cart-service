package com.gianpaolo.caprara.purchase.cart.dtos.responses

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateOrderDTOResponseTest {

    @Test
    fun `create order dto response expected values`() {
        val createOrderDTOResponse = CreateOrderDTOResponse(
            id = 1,
            price = 10.0,
            vat = 22.0,
            items = listOf(
                OrderItemDTOResponse("1", 1, 10.0, 22.0),
                OrderItemDTOResponse("2", 2, 12.0, 10.5)
            )
        )

        assertEquals(createOrderDTOResponse.id, 1)
        assertEquals(createOrderDTOResponse.price, 10.0)
        assertEquals(createOrderDTOResponse.vat, 22.0)
        assertEquals(createOrderDTOResponse.items.size, 2)
        assertEquals(createOrderDTOResponse.items[0].id, "1")
        assertEquals(createOrderDTOResponse.items[0].quantity, 1)
        assertEquals(createOrderDTOResponse.items[0].price, 10.0)
        assertEquals(createOrderDTOResponse.items[0].vat, 22.0)
        assertEquals(createOrderDTOResponse.items[1].id, "2")
        assertEquals(createOrderDTOResponse.items[1].quantity, 2)
        assertEquals(createOrderDTOResponse.items[1].price, 12.0)
        assertEquals(createOrderDTOResponse.items[1].vat, 10.5)
    }
}