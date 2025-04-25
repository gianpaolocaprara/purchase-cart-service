package com.gianpaolo.caprara.purchase.cart.providers.repositories

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.sql.Connection
import java.sql.DriverManager

@Configuration
class ConnectionProvider(
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.username}") val user: String
) {
    @Bean
    @Scope(SCOPE_SINGLETON)
    fun connection(): Connection {
        return DriverManager.getConnection(url, user, "")
    }
}