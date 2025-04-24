package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderItemEntityTest {

    @Test
    fun `create order item entity with correct values`() {
        val entity = OrderItemEntity(1, 30.0, 0.30, 3)

        assertThat(entity.productId).isEqualTo(1)
        assertThat(entity.price).isEqualTo(30.0)
        assertThat(entity.vat).isEqualTo(0.30)
        assertThat(entity.quantity).isEqualTo(3)
    }
}