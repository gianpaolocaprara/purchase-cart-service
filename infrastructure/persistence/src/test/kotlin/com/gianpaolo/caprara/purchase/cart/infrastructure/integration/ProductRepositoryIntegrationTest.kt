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
    fun `setup repository`() {
        repository = ProductRepositoryImpl(connection)
    }

    @Test
    fun `find by id expected result`() {
        val product = Product(id = 1, name = "Test", price = 100.0, vat = 22.0)
        repository.save(product)

        val found = repository.findById(1)

        assertThat(found).isNotNull
        assertThat(product).isEqualTo(found)
    }

    @Test
    fun `find by id throws data not found exception if product not exists`() {
        assertThrows<DataNotFoundException> { repository.findById(1) }
    }

}