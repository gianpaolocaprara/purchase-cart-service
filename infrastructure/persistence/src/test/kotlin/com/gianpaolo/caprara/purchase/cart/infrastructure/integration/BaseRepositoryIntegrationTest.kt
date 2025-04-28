package com.gianpaolo.caprara.purchase.cart.infrastructure.integration

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

abstract class BaseRepositoryIntegrationTest {

    companion object {
        @JvmStatic
        protected lateinit var connection: Connection

        @JvmStatic
        @BeforeAll
        fun `set up database and tables`() {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
            val statement: Statement = connection.createStatement()

            statement.execute(
                """
            CREATE TABLE IF NOT EXISTS products (
                id INT PRIMARY KEY,
                name VARCHAR(255),
                price DOUBLE,
                vat DOUBLE
            )
        """.trimIndent()
            )

            statement.execute(
                """
            CREATE TABLE IF NOT EXISTS orders (
                id INT PRIMARY KEY,
                price DOUBLE,
                vat DOUBLE
            )
        """.trimIndent()
            )

            statement.execute(
                """
            CREATE TABLE IF NOT EXISTS order_items (
                order_id INT,
                product_id INT,
                price DOUBLE,
                vat DOUBLE,
                quantity INT,
                PRIMARY KEY (order_id, product_id),
                FOREIGN KEY (order_id) REFERENCES orders(id),
                FOREIGN KEY (product_id) REFERENCES products(id)
            )
        """.trimIndent()
            )
        }

        @JvmStatic
        @AfterAll
        fun `close database connection`() {
            connection.close()
        }
    }

    @BeforeEach
    fun `clean database`() {
        connection.createStatement().use {
            it.executeUpdate("DELETE FROM order_items")
            it.executeUpdate("DELETE FROM orders")
            it.executeUpdate("DELETE FROM products")
        }
    }
}