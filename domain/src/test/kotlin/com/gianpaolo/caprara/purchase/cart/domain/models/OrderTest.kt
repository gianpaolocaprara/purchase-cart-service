package com.gianpaolo.caprara.purchase.cart.domain.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun `create order model expected values`() {
        val order = Order(
            id = 123,
            items = listOf(
                OrderItem(product = Product(id = "1", name = "Product 1", price = 10.0, vat = 22.0), quantity = 10),
                OrderItem(product = Product(id = "2", name = "Product 2", price = 12.0, vat = 10.5), quantity = 2),
            ),
            price = 22.0,
            vat = 32.5
        )

        assertThat(order.id).isEqualTo(123)
        assertThat(order.items.size).isEqualTo(2)
        assertThat(order.items[0].javaClass).isEqualTo(OrderItem::class.java)
        assertThat(order.items[0].product.id).isEqualTo("1")
        assertThat(order.items[1].javaClass).isEqualTo(OrderItem::class.java)
        assertThat(order.items[1].product.id).isEqualTo("2")
        assertThat(order.price).isEqualTo(22.0)
        assertThat(order.vat).isEqualTo(32.5)
    }
}