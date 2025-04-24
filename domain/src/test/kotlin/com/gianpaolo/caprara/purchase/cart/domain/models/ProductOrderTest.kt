package com.gianpaolo.caprara.purchase.cart.domain.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductOrderTest {

    @Test
    fun `create product order model expected values`() {
        val productOrder = ProductOrder(
            id = "1",
            name = "Product 1",
            price = 10.0,
            vat = 22.0,
            quantity = 10
        )

        assertThat(productOrder.id).isEqualTo("1")
        assertThat(productOrder.name).isEqualTo("Product 1")
        assertThat(productOrder.price).isEqualTo(10.0)
        assertThat(productOrder.vat).isEqualTo(22.0)
        assertThat(productOrder.quantity).isEqualTo(10)
    }
}