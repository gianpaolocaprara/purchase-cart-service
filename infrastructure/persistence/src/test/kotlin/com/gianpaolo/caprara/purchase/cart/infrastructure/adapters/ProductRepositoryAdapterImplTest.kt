package com.gianpaolo.caprara.purchase.cart.infrastructure.adapters

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ProductRepositoryAdapterImplTest {

    private lateinit var productRepositoryAdapterImpl: ProductRepositoryAdapterImpl
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun `set up`() {
        productRepository = mockk(relaxed = true)
        productRepositoryAdapterImpl = ProductRepositoryAdapterImpl(repository = productRepository)
    }

    @Test
    fun `get by id call repository as expected`() {
        every { productRepository.findById(1) } returns Optional.of(
            Product(
                id = 1,
                name = "Product 1",
                price = 10.0,
                vat = 22.0
            )
        )

        productRepositoryAdapterImpl.findById(id = 1)

        verify(exactly = 1) { productRepository.findById(1) }
    }

    @Test
    fun `get by id return value as expected`() {
        every { productRepository.findById(1) } returns Optional.of(
            Product(
                id = 1,
                name = "Product 1",
                price = 10.0,
                vat = 22.0
            )
        )

        val response = productRepositoryAdapterImpl.findById(id = 1)

        assertThat(response.id).isEqualTo(1)
        assertThat(response.name).isEqualTo("Product 1")
        assertThat(response.price).isEqualTo(10.0)
        assertThat(response.vat).isEqualTo(22.0)
    }

    @Test
    fun `get by id throws not found exception if product not found`() {
        every { productRepository.findById(1) } returns Optional.empty()

        val error = assertThrows<DataNotFoundException> { productRepositoryAdapterImpl.findById(id = 1) }

        assertThat(error.message).isEqualTo("Product with id 1 not found")
    }
}