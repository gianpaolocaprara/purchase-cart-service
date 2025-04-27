package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DatabaseException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.PreparedStatement

class OrderRepositoryImplTest {

    private lateinit var connection: Connection
    private lateinit var preparedStatement: PreparedStatement
    private lateinit var repository: OrderRepositoryImpl

    @BeforeEach
    fun `setup repository`() {
        connection = mockk(relaxed = true)
        preparedStatement = mockk(relaxed = true)

        every { connection.prepareStatement(any()) } returns preparedStatement

        repository = OrderRepositoryImpl(connection)
    }

    @AfterEach
    fun `clear all mocks`() {
        clearAllMocks()
    }

    @Test
    fun `save should call method as expected`() {
        val product = Product(id = 1, price = 1.00, vat = 0.10)
        val orderItem = OrderItem(product = product, quantity = 2)
        val order = Order(id = null, price = 2.00, vat = 0.20, items = listOf(orderItem))

        val result = repository.save(order)

        verify(exactly = 2) { connection.prepareStatement(any()) }
        verify(exactly = 2) { preparedStatement.executeUpdate() }

        assertThat(result.id).isNotNull
        assertThat(result.price).isEqualTo(order.price)
        assertThat(result.vat).isEqualTo(order.vat)
        assertThat(result.items).isEqualTo(order.items)
    }

    @Test
    fun `save throw DatabaseException when error occurs and rollback is executed`() {
        val product = Product(id = 1, price = 1.00, vat = 0.10)
        val orderItem = OrderItem(product = product, quantity = 2)
        val order = Order(id = null, price = 2.00, vat = 0.20, items = listOf(orderItem))

        every { connection.prepareStatement(any()) } throws RuntimeException("Database error")

        assertThrows(DatabaseException::class.java) {
            repository.save(order)
        }

        verify { connection.rollback() }
    }
}