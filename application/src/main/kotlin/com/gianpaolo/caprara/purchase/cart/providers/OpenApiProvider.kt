package com.gianpaolo.caprara.purchase.cart.providers

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!cli")
@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Purchase Cart Service")
                    .description("Purchase Cart Service documentation")
                    .version("1.0.0")
            )
    }
}