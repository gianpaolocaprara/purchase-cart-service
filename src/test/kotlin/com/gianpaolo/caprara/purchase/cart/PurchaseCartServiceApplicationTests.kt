package com.gianpaolo.caprara.purchase.cart

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class PurchaseCartServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

}
