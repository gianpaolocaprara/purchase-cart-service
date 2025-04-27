package com.gianpaolo.caprara.purchase.cart.infrastructure.integration

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DatabaseException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.OrderRepositoryImpl
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.ProductRepositoryImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderRepositoryIntegrationTest : BaseRepositoryIntegrationTest() {

    private lateinit var repository: OrderRepositoryImpl
    private lateinit var productRepository: ProductRepositoryImpl

    @BeforeEach
    fun `setup repository and data`() {
        repository = OrderRepositoryImpl(connection)
        productRepository = ProductRepositoryImpl(connection)
        connection.createStatement().use {
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (1, 'Product 1', 1.00, 0.10)")
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (2, 'Product 2', 2.00, 0.20)")
        }
    }

    @Test
    fun `save expected id is not null and not zero`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1, price = 1.00, vat = 0.10), quantity = 2),
                OrderItem(product = Product(id = 2, price = 2.00, vat = 0.20), quantity = 1),
            ),
            price = 4.00,
            vat = 0.30
        )

        val result = repository.save(order)

        assertThat(result.id).isNotNull
        assertThat(result.id).isNotZero
        assertThat(result.id).isPositive
    }

    @Test
    fun `save throws database exception if product not exists`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1, price = 1.00, vat = 0.10), quantity = 2),
                OrderItem(product = Product(id = 999, price = 2.00, vat = 0.20), quantity = 1),
            ),
            price = 4.00,
            vat = 0.30
        )

        assertThrows<DatabaseException> { repository.save(order) }
    }

}