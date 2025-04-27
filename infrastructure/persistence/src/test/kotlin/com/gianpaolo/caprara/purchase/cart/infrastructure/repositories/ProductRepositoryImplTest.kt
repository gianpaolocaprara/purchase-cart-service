package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
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
import java.sql.ResultSet

class ProductRepositoryImplTest {

    private lateinit var connection: Connection
    private lateinit var preparedStatement: PreparedStatement
    private lateinit var resultSet: ResultSet
    private lateinit var repository: ProductRepositoryImpl

    @BeforeEach
    fun `setup repository`() {
        connection = mockk(relaxed = true)
        preparedStatement = mockk(relaxed = true)
        resultSet = mockk(relaxed = true)

        every { connection.prepareStatement(any()) } returns preparedStatement
        every { preparedStatement.executeQuery() } returns resultSet

        repository = ProductRepositoryImpl(connection)
    }

    @AfterEach
    fun `clear all mocks`() {
        clearAllMocks()
    }

    @Test
    fun `find by id should return expected product`() {
        every { resultSet.next() } returns true
        every { resultSet.getInt("id") } returns 1
        every { resultSet.getString("name") } returns "Test Product"
        every { resultSet.getDouble("price") } returns 1.00
        every { resultSet.getDouble("vat") } returns 0.10

        val product = repository.findById(1)

        verify(exactly = 1) { connection.prepareStatement(any<String>()) }
        verify(exactly = 1) { preparedStatement.setInt(1, 1) }
        verify(exactly = 1) { preparedStatement.executeQuery() }
        verify(exactly = 1) { resultSet.next() }
        assertThat(product.id).isEqualTo(1)
        assertThat(product.name).isEqualTo("Test Product")
        assertThat(product.price).isEqualTo(1.00)
        assertThat(product.vat).isEqualTo(0.10)
    }

    @Test
    fun `find by id throw DataNotFoundException when product not found`() {
        every { resultSet.next() } returns false

        val exception = assertThrows(DataNotFoundException::class.java) {
            repository.findById(999)
        }

        assertThat(exception.message).isEqualTo("Product with id 999 not found")
    }

}