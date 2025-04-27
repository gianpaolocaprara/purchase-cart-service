package com.gianpaolo.caprara.purchase.cart.domain.usecases

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepository
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CreateOrderUseCase(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(CreateOrderUseCase::class.java)

    fun apply(order: Order): Order {
        return this.orderRepository.save(order = createOrder(orderItems = createOrderItems(orderItems = order.items)))
    }

    private fun createOrder(orderItems: List<OrderItem>): Order = Order(
        items = orderItems,
        price = orderItems.sumOf { it.product.price!! },
        vat = orderItems.sumOf { it.product.vat!! }
    )

    private fun createOrderItems(orderItems: List<OrderItem>): List<OrderItem> = orderItems.map {
        createOrderItem(product = findProduct(productId = it.product.id), quantity = it.quantity)
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

    private fun findProduct(productId: Int): Product = try {
        this.productRepository.findById(productId)
    } catch (_: DataNotFoundException) {
        logger.warn("Product with id $productId not found.")
        throw InvalidParameterException("Product with id $productId not found.")
    }

}