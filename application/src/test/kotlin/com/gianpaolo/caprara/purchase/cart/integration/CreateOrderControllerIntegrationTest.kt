package com.gianpaolo.caprara.purchase.cart.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gianpaolo.caprara.purchase.cart.PurchaseCartServiceApplication
import com.gianpaolo.caprara.purchase.cart.controllers.ordersPath
import com.gianpaolo.caprara.purchase.cart.controllers.post
import com.gianpaolo.caprara.purchase.cart.dtos.requests.CreateOrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.OrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.OrderItemDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [PurchaseCartServiceApplication::class])
@ActiveProfiles("test")
class CreateOrderControllerIntegrationTest : AbstractBaseIntegrationTest() {

    @BeforeEach
    fun `setup data`() {
        connection.createStatement().use {
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (1, 'Product A', 2.00, 0.20)")
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (2, 'Product B', 1.50, 0.15)")
            it.executeUpdate("INSERT INTO products (id, name, price, vat) VALUES (3, 'Product C', 3.00, 0.30)")
        }
    }

    @Test
    fun `create a new order expected status created`() {
        val request = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 5),
                    OrderItemDTO(productId = 3, quantity = 1)
                )
            )
        )
        mvc.post(ordersPath, request).andExpect { status { isCreated() } }
    }

    @Test
    fun `create a new order expected result`() {
        val request = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 5),
                    OrderItemDTO(productId = 3, quantity = 1)
                )
            )
        )
        val response: String = mvc.post(
            path = ordersPath,
            entity = request
        ).andReturn()
            .response
            .contentAsString

        val objectMapper = ObjectMapper().registerKotlinModule()
        val responseMap: Map<String, Any> = objectMapper.readValue(content = response)
        val orderId = responseMap["order_id"] as Int
        assertThat(orderId).isNotNull
        assertThat(orderId).isNotZero
        assertThat(orderId).isPositive
        assertThat(responseMap["order_price"]).isEqualTo(12.50)
        assertThat(responseMap["order_vat"]).isEqualTo(1.25)
        assertThat(responseMap["items"]).isEqualTo(
            listOf(
                mapOf(
                    "product_id" to 1,
                    "quantity" to 1,
                    "price" to 2.00,
                    "vat" to 0.20
                ),
                mapOf(
                    "product_id" to 2,
                    "quantity" to 5,
                    "price" to 7.50,
                    "vat" to 0.75
                ),
                mapOf(
                    "product_id" to 3,
                    "quantity" to 1,
                    "price" to 3.00,
                    "vat" to 0.30
                )
            )
        )
    }

    @Test
    fun `create a new order expected bad request if a product not found`() {
        val request = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 200, quantity = 5)
                )
            )
        )

        mvc.post(
            path = ordersPath,
            entity = request
        ).andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_DATA") }
            jsonPath("$.message") { value("Product with id 200 not found.") }
        }
    }

    @Test
    fun `create a new order expected bad request if order item list is empty`() {
        val request = CreateOrderDTO(order = OrderDTO(items = emptyList()))

        mvc.post(
            path = ordersPath,
            entity = request
        ).andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_DATA") }
            jsonPath("$.message") { value("Order must contain at least one item.") }
        }
    }

    @Test
    fun `create a new order expected bad request if a order item has quantity 0`() {
        val request = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = 0)
                )
            )
        )

        mvc.post(
            path = ordersPath,
            entity = request
        ).andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_DATA") }
            jsonPath("$.message") { value("Order must contain only positive quantities.") }
        }
    }

    @Test
    fun `create a new order expected bad request if a order item has negative quantity`() {
        val request = CreateOrderDTO(
            order = OrderDTO(
                items = listOf(
                    OrderItemDTO(productId = 1, quantity = 1),
                    OrderItemDTO(productId = 2, quantity = -1)
                )
            )
        )

        mvc.post(
            path = ordersPath,
            entity = request
        ).andExpect {
            status { isBadRequest() }
            jsonPath("$.code") { value("INVALID_DATA") }
            jsonPath("$.message") { value("Order must contain only positive quantities.") }
        }
    }
}