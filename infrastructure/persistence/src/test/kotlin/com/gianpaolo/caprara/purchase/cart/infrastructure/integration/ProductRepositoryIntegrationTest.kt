package com.gianpaolo.caprara.purchase.cart.infrastructure.integration

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.ProductRepositoryImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductRepositoryIntegrationTest : BaseRepositoryIntegrationTest() {

    private lateinit var repository: ProductRepositoryImpl

    @BeforeEach
    fun `setup repository and data`() {
        repository = ProductRepositoryImpl(connection)
        connection.createStatement().use {
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (1, 'Product A', 2.00, 0.20)")
        }
    }

    @Test
    fun `find by id expected result`() {
        val product: Product = repository.findById(1)

        assertThat(product).isNotNull
        assertThat(product.id).isEqualTo(1)
        assertThat(product.name).isEqualTo("Product A")
        assertThat(product.price).isEqualTo(2.00)
        assertThat(product.vat).isEqualTo(0.20)
    }

    @Test
    fun `find by id throws data not found exception if product not exists`() {
        val message = assertThrows<DataNotFoundException> { repository.findById(999) }
        assertThat(message.message).isEqualTo("Product with id 999 not found")
    }

}