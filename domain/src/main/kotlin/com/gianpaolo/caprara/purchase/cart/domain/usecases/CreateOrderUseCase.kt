package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.ProductNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product

class CreateOrderUseCase {
    fun apply(order: Order): Order {
        val existingProducts: List<Product> = listOf(
            Product(id = 1, price = 20.0, vat = 0.20),
            Product(id = 2, price = 3.0, vat = 0.30),
            Product(id = 3, price = 5.0, vat = 0.50),
        )

        val newOrderItem: List<OrderItem> =
            order.items.map { item ->
                val product: Product = existingProducts
                    .find { product -> item.product.id == product.id }
                    ?: throw ProductNotFoundException("Product with id ${item.product.id} not found.")
                OrderItem(
                    product = Product(
                        id = product.id,
                        price = product.price!! * item.quantity,
                        vat = product.vat!! * item.quantity
                    ),
                    quantity = item.quantity
                )
            }
        return Order(
            id = 111,
            items = newOrderItem,
            price = newOrderItem.sumOf { it.product.price!! },
            vat = newOrderItem.sumOf { it.product.vat!! }
        )
    }
}