package com.gianpaolo.caprara.purchase.cart.dtos.responses

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CreateOrderDTOResponseTest {

    @Test
    fun `create order dto response expected values`() {
        val createOrderDTOResponse = CreateOrderDTOResponse(
            id = 1,
            price = 10.0,
            vat = 22.0,
            items = listOf(
                OrderItemDTOResponse(id = 1, quantity = 1, price = 10.0, vat = 22.0),
                OrderItemDTOResponse(id = 2, quantity = 2, price = 12.0, vat = 10.5)
            )
        )

        assertThat(createOrderDTOResponse.id).isEqualTo(1)
        assertThat(createOrderDTOResponse.price).isEqualTo(10.0)
        assertThat(createOrderDTOResponse.vat).isEqualTo(22.0)
        assertThat(createOrderDTOResponse.items.size).isEqualTo(2)
        assertThat(createOrderDTOResponse.items[0].id).isEqualTo(1)
        assertThat(createOrderDTOResponse.items[0].quantity).isEqualTo(1)
        assertThat(createOrderDTOResponse.items[0].price).isEqualTo(10.0)
        assertThat(createOrderDTOResponse.items[0].vat).isEqualTo(22.0)
        assertThat(createOrderDTOResponse.items[1].id).isEqualTo(2)
        assertThat(createOrderDTOResponse.items[1].quantity).isEqualTo(2)
        assertThat(createOrderDTOResponse.items[1].price).isEqualTo(12.0)
        assertThat(createOrderDTOResponse.items[1].vat).isEqualTo(10.5)
    }
}