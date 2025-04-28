package com.gianpaolo.caprara.purchase.cart.providers.usecases

import com.gianpaolo.caprara.purchase.cart.domain.repositories.OrderRepository
import com.gianpaolo.caprara.purchase.cart.domain.repositories.ProductRepository
import com.gianpaolo.caprara.purchase.cart.domain.usecases.CreateOrderUseCase
import com.gianpaolo.caprara.purchase.cart.domain.validators.CreateOrderValidator
import org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class OrderUseCaseProvider(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {
    @Bean
    @Scope(SCOPE_SINGLETON)
    fun createOrderUseCase() = CreateOrderUseCase(
        productRepository = productRepository,
        orderRepository = orderRepository,
        validator = CreateOrderValidator()
    )
}