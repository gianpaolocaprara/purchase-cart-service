package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepository
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepositoryAdapter
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateOrderUseCaseTest {
    private lateinit var createOrderUseCase: CreateOrderUseCase
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter
    private lateinit var orderRepository: OrderRepository

    @BeforeEach
    fun `set up`() {
        productRepositoryAdapter = mockk(relaxed = true)
        orderRepository = mockk(relaxed = true)
        createOrderUseCase = CreateOrderUseCase(
            productRepositoryAdapter = productRepositoryAdapter, orderRepository = orderRepository
        )
    }

    @Test
    fun `test apply should call product repository in order to retrieve products`() {
        every { productRepositoryAdapter.findById(id = 1) } returns Product(id = 1, price = 20.0, vat = 0.20)
        every { productRepositoryAdapter.findById(id = 2) } returns Product(id = 2, price = 3.0, vat = 0.30)
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1), OrderItem(product = Product(id = 2), quantity = 2)
            )
        )

        createOrderUseCase.apply(order = order)

        verifyOrder {
            productRepositoryAdapter.findById(1)
            productRepositoryAdapter.findById(2)
        }
    }

    @Test
    fun `test apply should call order repository in order to save order`() {
        every { productRepositoryAdapter.findById(id = 1) } returns Product(id = 1, price = 20.0, vat = 0.20)
        every { productRepositoryAdapter.findById(id = 2) } returns Product(id = 2, price = 3.0, vat = 0.30)
        every { orderRepository.save(any()) } returns Order(id = 111, items = emptyList())
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1), OrderItem(product = Product(id = 2), quantity = 2)
            )
        )

        createOrderUseCase.apply(order = order)

        verifyOrder {
            productRepositoryAdapter.findById(1)
            productRepositoryAdapter.findById(2)
            orderRepository.save(
                Order(
                    id = null, items = listOf(
                        OrderItem(product = Product(id = 1, name = null, price = 20.0, vat = 0.2), quantity = 1),
                        OrderItem(product = Product(id = 2, name = null, price = 6.0, vat = 0.6), quantity = 2)
                    ), price = 26.0, vat = 0.8
                )
            )
        }
    }

    @Test
    fun `test apply should return expected order`() {
        every { productRepositoryAdapter.findById(id = 1) } returns Product(id = 1, price = 20.0, vat = 0.20)
        every { productRepositoryAdapter.findById(id = 2) } returns Product(id = 2, price = 3.0, vat = 0.30)
        every { productRepositoryAdapter.findById(id = 3) } returns Product(id = 3, price = 5.0, vat = 0.50)
        every { orderRepository.save(any()) } returns Order(
            id = 123,
            items = listOf(
                OrderItem(product = Product(id = 1, price = 20.00, vat = 0.20), quantity = 1),
                OrderItem(product = Product(id = 2, price = 6.00, vat = 0.60), quantity = 2),
                OrderItem(product = Product(id = 3, price = 5.00, vat = 0.50), quantity = 1),
            ),
            price = 31.0,
            vat = 1.30
        )
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1),
                OrderItem(product = Product(id = 2), quantity = 2),
                OrderItem(product = Product(id = 3), quantity = 1),
            )
        )

        val orderCreated: Order = createOrderUseCase.apply(order = order)

        assertThat(orderCreated.id).isEqualTo(123)
        val slot = slot<Order>()
        verify(exactly = 1) { orderRepository.save(capture(slot)) }
        assertThat(slot.captured.price).isEqualTo(31.00)
        assertThat(slot.captured.vat).isEqualTo(1.30)
        assertThat(slot.captured.items.size).isEqualTo(3)
        assertThat(slot.captured.items[0].product.id).isEqualTo(1)
        assertThat(slot.captured.items[0].product.price).isEqualTo(20.00)
        assertThat(slot.captured.items[0].product.vat).isEqualTo(0.20)
        assertThat(slot.captured.items[0].quantity).isEqualTo(1)
        assertThat(slot.captured.items[1].product.id).isEqualTo(2)
        assertThat(slot.captured.items[1].product.price).isEqualTo(6.00)
        assertThat(slot.captured.items[1].product.vat).isEqualTo(0.60)
        assertThat(slot.captured.items[1].quantity).isEqualTo(2)
        assertThat(slot.captured.items[2].product.id).isEqualTo(3)
        assertThat(slot.captured.items[2].product.price).isEqualTo(5.00)
        assertThat(slot.captured.items[2].product.vat).isEqualTo(0.5)
        assertThat(slot.captured.items[2].quantity).isEqualTo(1)
    }

    @Test
    fun `test apply should throw exception when product not found`() {
        every { productRepositoryAdapter.findById(id = 1) } returns Product(id = 1, price = 20.0, vat = 0.20)
        every { productRepositoryAdapter.findById(id = 5) } throws DataNotFoundException("Product with id 5 not found.")
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 1), OrderItem(product = Product(id = 5), quantity = 2)
            )
        )

        val assertThrows = assertThrows<InvalidParameterException> { createOrderUseCase.apply(order = order) }

        assertThat(assertThrows.message).isEqualTo("Product with id 5 not found.")
    }
}