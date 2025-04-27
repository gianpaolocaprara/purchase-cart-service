package com.gianpaolo.caprara.purchase.cart.domain.validators

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateOrderValidatorTest {

    private val validator = CreateOrderValidator()

    @Test
    fun `validate throws exception if order is invalid`() {
        val order = Order(items = emptyList())

        val exception = assertThrows<InvalidParameterException> { validator.validate(order) }

        assertThat(exception.message).isEqualTo("Order must contain at least one item.")
    }

    @Test
    fun `validate throws exception if order has 0 quantity`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 2),
                OrderItem(product = Product(id = 2), quantity = 0)
            )
        )

        val exception = assertThrows<InvalidParameterException> { validator.validate(order) }

        assertThat(exception.message).isEqualTo("Order must contain only positive quantities.")
    }

    @Test
    fun `validate throws exception if order has negative quantity`() {
        val order = Order(
            items = listOf(
                OrderItem(product = Product(id = 1), quantity = 2),
                OrderItem(product = Product(id = 2), quantity = -1)
            )
        )

        val exception = assertThrows<InvalidParameterException> { validator.validate(order) }

        assertThat(exception.message).isEqualTo("Order must contain only positive quantities.")
    }

}