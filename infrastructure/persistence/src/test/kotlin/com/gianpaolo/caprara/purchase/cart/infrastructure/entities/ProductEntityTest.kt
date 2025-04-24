package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductEntityTest {

    @Test
    fun `create product entity with correct values`() {
        val entity = ProductEntity(1, "Product 1", 20.0, 0.20)

        assertThat(entity.id).isEqualTo(1)
        assertThat(entity.price).isEqualTo(20.0)
        assertThat(entity.vat).isEqualTo(0.20)
        assertThat(entity.name).isEqualTo("Product 1")
    }

    @Test
    fun `toModel method expected result Product with correct values`() {
        val entity = ProductEntity(1, "Product 1", 20.0, 0.20)

        val model = entity.toModel()

        assertThat(model.id).isEqualTo(1)
        assertThat(model.price).isEqualTo(20.0)
        assertThat(model.vat).isEqualTo(0.20)
        assertThat(model.name).isEqualTo("Product 1")
    }
}