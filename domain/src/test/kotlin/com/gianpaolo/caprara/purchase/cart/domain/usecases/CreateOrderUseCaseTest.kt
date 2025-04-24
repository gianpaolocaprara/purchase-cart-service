package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateOrderUseCaseTest {

    private lateinit var createOrderUseCase: CreateOrderUseCase

    @BeforeEach
    fun `set up`() {
        createOrderUseCase = CreateOrderUseCase()
    }

    @Test
    fun `test apply should return expected order`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1),
                OrderItem(product = Product(id = 2), quantity = 2),
                OrderItem(product = Product(id = 3), quantity = 1),
            )
        )
        val orderCreated: Order = createOrderUseCase.apply(order = order)

        assertThat(orderCreated.id).isEqualTo(111)
        assertThat(orderCreated.price).isEqualTo(31.00)
        assertThat(orderCreated.vat).isEqualTo(1.30)
        assertThat(orderCreated.items.size).isEqualTo(3)
        assertThat(orderCreated.items[0].product.id).isEqualTo(1)
        assertThat(orderCreated.items[0].product.price).isEqualTo(20.00)
        assertThat(orderCreated.items[0].product.vat).isEqualTo(0.20)
        assertThat(orderCreated.items[0].quantity).isEqualTo(1)
        assertThat(orderCreated.items[1].product.id).isEqualTo(2)
        assertThat(orderCreated.items[1].product.price).isEqualTo(6.00)
        assertThat(orderCreated.items[1].product.vat).isEqualTo(0.60)
        assertThat(orderCreated.items[1].quantity).isEqualTo(2)
        assertThat(orderCreated.items[2].product.id).isEqualTo(3)
        assertThat(orderCreated.items[2].product.price).isEqualTo(5.00)
        assertThat(orderCreated.items[2].product.vat).isEqualTo(0.5)
        assertThat(orderCreated.items[2].quantity).isEqualTo(1)
    }

    @Test
    fun `test apply should throw exception when product not found`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1),
                OrderItem(product = Product(id = 5), quantity = 2)
            )
        )
        val assertThrows = assertThrows<InvalidParameterException> { createOrderUseCase.apply(order = order) }
        assertThat(assertThrows.message).isEqualTo("Product with id 5 not found.")
    }
}