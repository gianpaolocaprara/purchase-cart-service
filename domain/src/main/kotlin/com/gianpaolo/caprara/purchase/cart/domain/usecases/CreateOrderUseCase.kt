package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepository

class CreateOrderUseCase(
    private val productRepository: ProductRepository
) {
    fun apply(order: Order): Order {
        val newOrderItemList: List<OrderItem> =
            order.items.map { item ->
                val product: Product = findProduct(productId = item.product.id)
                createOrderItem(product = product, quantity = item.quantity)
            }
        return Order(
            id = 111,
            items = newOrderItemList,
            price = newOrderItemList.sumOf { it.product.price!! },
            vat = newOrderItemList.sumOf { it.product.vat!! }
        )
    }

    private fun findProduct(productId: Int): Product = try {
        this.productRepository.findById(productId)
    } catch (_: Exception) {
        throw InvalidParameterException("Product with id $productId not found.")
    }

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