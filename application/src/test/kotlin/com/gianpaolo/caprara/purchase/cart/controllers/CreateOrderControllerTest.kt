package com.gianpaolo.caprara.purchase.cart.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.usecases.CreateOrderUseCase
import com.gianpaolo.caprara.purchase.cart.dtos.requests.CreateOrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.OrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.OrderItemDTO
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class CreateOrderControllerTest {

    private lateinit var mvc: MockMvc
    private lateinit var controller: CreateOrderController
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @BeforeEach
    fun setUp() {
        createOrderUseCase = mockk(relaxed = true)
        controller = CreateOrderController(createOrderUseCase)
        mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ErrorController()).build()
    }

    @Test
    fun `create order expected status created`() {
        mvc.post(
            path = ordersPath,
            entity = request(items = listOf(OrderItemDTO(productId = 1, quantity = 1)))
        ).andExpect { status { isCreated() } }
    }

    @Test
    fun `create order expected use case call`() {
        mvc.post(
            path = ordersPath,
            entity = request(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 5),
                    OrderItemDTO(productId = 3, quantity = 2)
                )
            )
        )

        verify(exactly = 1) {
            createOrderUseCase.apply(
                Order(
                    items = listOf(
                        OrderItem(product = Product(id = 1), quantity = 1),
                        OrderItem(product = Product(id = 2), quantity = 5),
                        OrderItem(product = Product(id = 3), quantity = 2),
                    )
                )
            )
        }
    }

    @Test
    fun `create order expected result`() {
        every { createOrderUseCase.apply(any()) } returns Order(
            id = 123,
            items = listOf(
                OrderItem(product = Product(id = 1, name = "Product 1", price = 20.0, vat = 10.5), quantity = 1),
                OrderItem(product = Product(id = 2, name = "Product 2", price = 15.0, vat = 3.0), quantity = 5),
                OrderItem(product = Product(id = 3, name = "Product 3", price = 30.0, vat = 22.0), quantity = 2),
            ),
            price = 65.0,
            vat = 35.5
        )
        val response: String = mvc.post(
            path = ordersPath,
            entity = request(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 5),
                    OrderItemDTO(productId = 3, quantity = 2)
                )
            )
        ).andReturn()
            .response
            .contentAsString

        val objectMapper = ObjectMapper().registerKotlinModule()
        val responseMap: Map<String, Any> = objectMapper.readValue(content = response)
        assertThat(responseMap["order_id"]).isEqualTo(123)
        assertThat(responseMap["order_price"]).isEqualTo(65.0)
        assertThat(responseMap["order_vat"]).isEqualTo(35.5)
        assertThat(responseMap["items"]).isEqualTo(
            listOf(
                mapOf(
                    "product_id" to 1,
                    "quantity" to 1,
                    "price" to 20.0,
                    "vat" to 10.5
                ),
                mapOf(
                    "product_id" to 2,
                    "quantity" to 5,
                    "price" to 15.0,
                    "vat" to 3.0
                ),
                mapOf(
                    "product_id" to 3,
                    "quantity" to 2,
                    "price" to 30.0,
                    "vat" to 22.0
                )
            )
        )
    }

    @Test
    fun `create order expected bad request if a product not found`() {
        every { createOrderUseCase.apply(any()) } throws InvalidParameterException("Product with id 3 not found.")
        mvc.post(
            path = ordersPath,
            entity = request(items = listOf(OrderItemDTO(productId = 3, quantity = 2)))
        ).andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_DATA") }
            jsonPath("$.message") { value("Product with id 3 not found.") }
        }
    }

    private fun request(items: List<OrderItemDTO>) = CreateOrderDTO(order = OrderDTO(items = items))

}