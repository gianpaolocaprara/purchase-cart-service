package com.gianpaolo.caprara.purchase.cart.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.ProductOrder
import com.gianpaolo.caprara.purchase.cart.domain.usecases.CreateOrderUseCase
import com.gianpaolo.caprara.purchase.cart.dtos.requests.CreateOrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.OrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.ProductDTO
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class CreateOrderControllerTest {

    private lateinit var mvc: MockMvc
    private lateinit var controller: CreateOrderController
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @BeforeEach
    fun setUp() {
        createOrderUseCase = mockk(relaxed = true)
        controller = CreateOrderController(createOrderUseCase)
        mvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun `create order expected status created`() {
        val createOrderDTO = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    ProductDTO("1", 1)
                )
            )
        )
        mvc.post(ordersPath) {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(createOrderDTO)
        }.andExpect { status { isCreated() } }
    }

    @Test
    fun `create order expected use case call`() {
        val createOrderDTO = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    ProductDTO("1", 1),
                    ProductDTO("2", 5),
                    ProductDTO("3", 2)
                )
            )
        )
        mvc.post(ordersPath) {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(createOrderDTO)
        }

        verify(exactly = 1) { createOrderUseCase.apply(
            Order(items = listOf(
                ProductOrder(id = "1", quantity = 1),
                ProductOrder(id = "2", quantity = 5),
                ProductOrder(id = "3", quantity = 2),
            ))
        ) }
    }

    @Test
    fun `create order expected result`() {
        val createOrderDTO = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    ProductDTO("1", 1),
                    ProductDTO("2", 5),
                    ProductDTO("3", 2)
                )
            )
        )
        every { createOrderUseCase.apply(any()) } returns Order(
            id = 123,
            items = listOf(
                ProductOrder(id = "1", name = "Product 1", quantity = 1, price = 20.0, vat = 10.5),
                ProductOrder(id = "2", name = "Product 2", quantity = 5, price = 15.0, vat = 3.0),
                ProductOrder(id = "3", name = "Product 3", quantity = 2, price = 30.0, vat = 22.0),
            ),
            price = 65.0,
            vat = 35.5
        )
        val response: String = mvc.post(ordersPath) {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(createOrderDTO)
        }.andReturn()
            .response
            .contentAsString

        val objectMapper = ObjectMapper().registerKotlinModule()
        val responseMap: Map<String, Any> = objectMapper.readValue(content = response)
        assertThat(responseMap["order_id"]).isEqualTo(123)
        assertThat(responseMap["order_price"]).isEqualTo(65.0)
        assertThat(responseMap["order_vat"]).isEqualTo(35.5)
        assertThat(responseMap["items"]).isEqualTo(listOf(
            mapOf(
                "product_id" to "1",
                "quantity" to 1,
                "price" to 20.0,
                "vat" to 10.5
            ),
            mapOf(
                "product_id" to "2",
                "quantity" to 5,
                "price" to 15.0,
                "vat" to 3.0
            ),
            mapOf(
                "product_id" to "3",
                "quantity" to 2,
                "price" to 30.0,
                "vat" to 22.0
            )
        ))
    }
}