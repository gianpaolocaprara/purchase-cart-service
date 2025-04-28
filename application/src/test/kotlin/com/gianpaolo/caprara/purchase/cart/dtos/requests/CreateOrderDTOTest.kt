package com.gianpaolo.caprara.purchase.cart.dtos.requests

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import org.assertj.core.api.Assertions.assertThat
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

        assertThat(createOrderDTO.order.items.size).isEqualTo(2)
        assertThat(createOrderDTO.order.items[0].productId).isEqualTo(1)
        assertThat(createOrderDTO.order.items[0].quantity).isEqualTo(1)
        assertThat(createOrderDTO.order.items[1].productId).isEqualTo(2)
        assertThat(createOrderDTO.order.items[1].quantity).isEqualTo(2)
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

        assertThat(order.items.size).isEqualTo(2)
        assertThat(order.items[0].product.id).isEqualTo(1)
        assertThat(order.items[0].quantity).isEqualTo(1)
        assertThat(order.items[1].product.id).isEqualTo(2)
        assertThat(order.items[1].quantity).isEqualTo(2)
    }
}