package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
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

        val newOrderItemList: List<OrderItem> =
            order.items.map { item ->
                val product: Product = findProduct(existingProducts = existingProducts, productId = item.product.id)
                createOrderItem(product = product, quantity = item.quantity)
            }
        return Order(
            id = 111,
            items = newOrderItemList,
            price = newOrderItemList.sumOf { it.product.price!! },
            vat = newOrderItemList.sumOf { it.product.vat!! }
        )
    }

    private fun findProduct(existingProducts: List<Product>, productId: Int): Product =
        existingProducts.find { product -> productId == product.id }
            ?: throw InvalidParameterException("Product with id $productId not found.")

    private fun createOrderItem(product: Product, quantity: Int): OrderItem =
        OrderItem(
            product = Product(
                id = product.id,
                price = product.price!! * quantity,
                vat = product.vat!! * quantity
            ),
            quantity = quantity
        )
}