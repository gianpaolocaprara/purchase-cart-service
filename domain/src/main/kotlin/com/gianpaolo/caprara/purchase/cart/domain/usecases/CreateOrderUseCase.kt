package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepository
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepositoryAdapter

class CreateOrderUseCase(
    private val productRepositoryAdapter: ProductRepositoryAdapter,
    private val orderRepository: OrderRepository
) {
    fun apply(order: Order): Order {
        val newOrderItemList: List<OrderItem> =
            order.items.map { item ->
                val product: Product = findProduct(productId = item.product.id)
                createOrderItem(product = product, quantity = item.quantity)
            }
        val newOrder = Order(
            items = newOrderItemList,
            price = newOrderItemList.sumOf { it.product.price!! },
            vat = newOrderItemList.sumOf { it.product.vat!! }
        )
        return this.orderRepository.save(newOrder)
    }

    private fun findProduct(productId: Int): Product = try {
        this.productRepositoryAdapter.findById(productId)
    } catch (_: DataNotFoundException) {
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