package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderEntityTest {

    @Test
    fun `create order entity with correct values`() {
        val entity = OrderEntity(
            id = 1,
            items = listOf(OrderItemEntity(1, 20.0, 0.20, 1)),
            price = 20.0,
            vat = 0.20
        )

        assertThat(entity.id).isEqualTo(1)
        assertThat(entity.price).isEqualTo(20.0)
        assertThat(entity.vat).isEqualTo(0.20)
        assertThat(entity.items.size).isEqualTo(1)
        assertThat(entity.items[0].productId).isEqualTo(1)
        assertThat(entity.items[0].price).isEqualTo(20.0)
        assertThat(entity.items[0].vat).isEqualTo(0.20)
        assertThat(entity.items[0].quantity).isEqualTo(1)
    }

    @Test
    fun `toModel method expected result Order with correct values`() {
        val entity = OrderEntity(
            id = 1,
            items = listOf(
                OrderItemEntity(productId = 2, price = 10.0, vat = 0.10, quantity = 1)
            ),
            price = 10.0,
            vat = 0.10
        )

        val model = entity.toModel()

        assertThat(model.id).isEqualTo(1)
        assertThat(model.price).isEqualTo(10.0)
        assertThat(model.vat).isEqualTo(0.10)
        assertThat(model.items).hasSize(1)
        val item = model.items.first()
        assertThat(item.product.id).isEqualTo(2)
        assertThat(item.product.price).isEqualTo(10.0)
        assertThat(item.product.vat).isEqualTo(0.10)
        assertThat(item.quantity).isEqualTo(1)
    }

    @Test
    fun `toEntity method expected order entity with correct values`() {
        val model = Order(
            id = 1,
            items = listOf(
                OrderItem(
                    product = Product(id = 2, name = "", price = 5.0, vat = 0.50),
                    quantity = 1
                )
            ),
            price = 5.0,
            vat = 0.50
        )

        val entity = model.toEntity()

        assertThat(entity.id).isEqualTo(1)
        assertThat(entity.price).isEqualTo(5.0)
        assertThat(entity.vat).isEqualTo(0.50)
        assertThat(entity.items).hasSize(1)
        val item = entity.items.first()
        assertThat(item.productId).isEqualTo(2)
        assertThat(item.price).isEqualTo(5.0)
        assertThat(item.vat).isEqualTo(0.50)
        assertThat(item.quantity).isEqualTo(1)
    }
}