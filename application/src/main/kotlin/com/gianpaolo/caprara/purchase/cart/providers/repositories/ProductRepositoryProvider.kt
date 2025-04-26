package com.gianpaolo.caprara.purchase.cart.providers.repositories

import com.gianpaolo.caprara.purchase.cart.infrastructure.repositories.ProductRepositoryImpl
import org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.sql.Connection

@Configuration
class ProductRepositoryProvider(private val connection: Connection) {
    @Bean
    @Scope(SCOPE_SINGLETON)
    fun productRepository() = ProductRepositoryImpl(connection)
}