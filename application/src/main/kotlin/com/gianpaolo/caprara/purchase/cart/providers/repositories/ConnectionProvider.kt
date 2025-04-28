package com.gianpaolo.caprara.purchase.cart.providers.repositories

import org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.sql.Connection
import javax.sql.DataSource

@Configuration
class ConnectionProvider(private val dataSource: DataSource) {
    @Bean
    @Scope(SCOPE_SINGLETON)
    fun connection(): Connection = dataSource.connection
}