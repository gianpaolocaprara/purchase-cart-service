package com.gianpaolo.caprara.purchase.cart.infrastructure.entities

import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "product")
data class ProductEntity(
    @Id val id: Int,
    val name: String,
    val price: Double,
    val vat: Double
)

fun ProductEntity.toModel() = Product(
    id = id,
    name = name,
    price = price,
    vat = vat
)