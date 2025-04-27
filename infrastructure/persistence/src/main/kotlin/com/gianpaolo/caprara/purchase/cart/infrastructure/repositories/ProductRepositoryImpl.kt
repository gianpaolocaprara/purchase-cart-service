package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection

class ProductRepositoryImpl(private val connection: Connection) : ProductRepository {

    private val logger: Logger = LoggerFactory.getLogger(ProductRepositoryImpl::class.java)

    companion object {
        private const val SELECT_PRODUCT_SQL = "SELECT * FROM products WHERE id = ?"
    }

    override fun findById(id: Int): Product {
        val statement = connection.prepareStatement(SELECT_PRODUCT_SQL)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()
        return if (resultSet.next()) {
            Product(
                id = resultSet.getInt("id"),
                name = resultSet.getString("name"),
                price = resultSet.getDouble("price"),
                vat = resultSet.getDouble("vat")
            )
        } else {
            logger.warn("Product with id $id not found.")
            throw DataNotFoundException("Product with id $id not found")
        }
    }
}