package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.models.OrderItem
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("order")
data class OrderEntity(
    @Id val id: Int? = null,
    val items: List<OrderItemEntity>,
    val price: Double,
    val vat: Double
)

fun OrderEntity.toModel() = Order(
    id = id,
    items = items.map {
        OrderItem(
            product = ProductEntity(it.productId, "", it.price, it.vat).toModel(),
            quantity = it.quantity
        )
    },
    price = price,
    vat = vat
)

fun Order.toEntity() = OrderEntity(
    id = id,
    items = items.map {
        OrderItemEntity(
            productId = it.product.id,
            price = it.product.price!!,
            vat = it.product.vat!!,
            quantity = it.quantity
        )
    },
    price = price!!,
    vat = vat!!
)