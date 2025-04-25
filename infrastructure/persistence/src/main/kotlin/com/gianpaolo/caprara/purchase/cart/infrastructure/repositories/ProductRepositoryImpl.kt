package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.DataNotFoundException
import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepository
import java.sql.Connection

class ProductRepositoryImpl(private val connection: Connection) : ProductRepository {

    companion object {
        const val INSERT_PRODUCT_SQL = "INSERT INTO products (id, name, price, vat) VALUES (?, ?, ?, ?)"
        const val SELECT_PRODUCT_SQL = "SELECT * FROM products WHERE id = ?"
    }

    override fun save(product: Product) {
        val statement = connection.prepareStatement(INSERT_PRODUCT_SQL)
        statement.setInt(1, product.id)
        statement.setString(2, product.name)
        statement.setDouble(3, product.price!!)
        statement.setDouble(4, product.vat!!)
        statement.executeUpdate()
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
        } else throw DataNotFoundException("Product with id $id not found")
    }
}