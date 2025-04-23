package com.gianpaolo.caprara.purchase.cart

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<PurchaseCartServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
