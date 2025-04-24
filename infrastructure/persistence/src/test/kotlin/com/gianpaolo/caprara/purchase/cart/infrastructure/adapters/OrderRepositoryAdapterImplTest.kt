package com.gianpaolo.caprara.purchase.cart.infrastructure.adapters

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.OrderRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderRepositoryAdapterImplTest {

    private lateinit var orderRepositoryAdapterImpl: OrderRepositoryAdapterImpl
    private lateinit var orderRepository: OrderRepository

    @BeforeEach
    fun `set up`() {
        orderRepository = mockk(relaxed = true)
        orderRepositoryAdapterImpl = OrderRepositoryAdapterImpl(repository = orderRepository)
    }

    @Test
    fun `save call repository as expected`() {
        val order = Order(items = emptyList(), price = 0.0, vat = 0.0)
        every {
            orderRepository.save(order)
        } returns Order(id = 1, items = emptyList(), price = 0.0, vat = 0.0)

        orderRepositoryAdapterImpl.save(order = order)

        verify(exactly = 1) { orderRepository.save(order) }
    }

    @Test
    fun `save return value as expected`() {
        val order = Order(
            items = listOf(OrderItem(product = Product(id = 1, price = 10.0, vat = 22.0), quantity = 1)),
            price = 10.0,
            vat = 22.0
        )
        every {
            orderRepository.save(order)
        } returns Order(
            id = 123,
            items = listOf(OrderItem(product = Product(id = 1, price = 10.0, vat = 22.0), quantity = 1)),
            price = 10.0,
            vat = 22.0
        )

        val response = orderRepositoryAdapterImpl.save(order = order)

        assertThat(response.id).isEqualTo(123)
        assertThat(response.items.size).isEqualTo(1)
        assertThat(response.items[0].product.id).isEqualTo(1)
        assertThat(response.items[0].quantity).isEqualTo(1)
        assertThat(response.items[0].product.price).isEqualTo(10.0)
        assertThat(response.items[0].product.vat).isEqualTo(22.0)
        assertThat(response.price).isEqualTo(10.0)
        assertThat(response.vat).isEqualTo(22.0)
    }
}