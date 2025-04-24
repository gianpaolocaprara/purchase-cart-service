package com.gianpaolo.caprara.purchase.cart.domain.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun `create product model expected values`() {
        val product = Product(
            id = "1",
            name = "Product 1",
            price = 10.0,
            vat = 22.0
        )

        assertThat(product.id).isEqualTo("1")
        assertThat(product.name).isEqualTo("Product 1")
        assertThat(product.price).isEqualTo(10.0)
        assertThat(product.vat).isEqualTo(22.0)
    }
}