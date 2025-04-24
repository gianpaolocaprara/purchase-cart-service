package com.gianpaolo.caprara.purchase.cart.domain.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderItemTest {

    @Test
    fun `create product order model expected values`() {
        val orderItem = OrderItem(
            product = Product(id = 1, name = "Product 1", price = 10.0, vat = 22.0),
            quantity = 10
        )

        assertThat(orderItem.product.id).isEqualTo(1)
        assertThat(orderItem.product.name).isEqualTo("Product 1")
        assertThat(orderItem.product.price).isEqualTo(10.0)
        assertThat(orderItem.product.vat).isEqualTo(22.0)
        assertThat(orderItem.quantity).isEqualTo(10)
    }
}