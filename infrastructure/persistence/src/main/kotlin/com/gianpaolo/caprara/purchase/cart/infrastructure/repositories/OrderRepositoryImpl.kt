package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DatabaseException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import kotlin.random.Random

class OrderRepositoryImpl(private val connection: Connection) : OrderRepository {

    private val logger: Logger = LoggerFactory.getLogger(OrderRepositoryImpl::class.java)

    companion object {
        private const val INSERT_ORDER_SQL = "INSERT INTO orders (id, price, vat) VALUES (?, ?, ?)"
        private const val INSERT_ORDER_ITEM_SQL =
            "INSERT INTO order_items (order_id, product_id, price, vat, quantity) VALUES (?, ?, ?, ?, ?)"
    }

    override fun save(order: Order): Order {
        try {
            logger.debug("Saving order.")
            connection.autoCommit = false
            val orderId = insertOrder(order = order)
            order.items.forEach {
                insertOrderItem(item = it, orderId = orderId)
            }
            return order.copy(id = orderId)
        } catch (e: Exception) {
            connection.rollback()
            throw DatabaseException("Exception while saving order: ${e.message}")
        } finally {
            logger.debug("Order saved.")
            connection.autoCommit = true
        }
    }

    private fun insertOrderItem(item: OrderItem, orderId: Int) {
        val orderItemStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)
        orderItemStatement.setInt(1, orderId)
        orderItemStatement.setInt(2, item.product.id)
        orderItemStatement.setDouble(3, item.product.price!!)
        orderItemStatement.setDouble(4, item.product.vat!!)
        orderItemStatement.setInt(5, item.quantity)
        orderItemStatement.executeUpdate()
    }

    private fun insertOrder(order: Order): Int {
        val orderId = Random.nextInt(1, Int.MAX_VALUE)
        logger.debug("Order id generated: $orderId")
        val orderStatement = connection.prepareStatement(INSERT_ORDER_SQL)
        orderStatement.setInt(1, orderId)
        orderStatement.setDouble(2, order.price!!)
        orderStatement.setDouble(3, order.vat!!)
        orderStatement.executeUpdate()
        return orderId
    }

}