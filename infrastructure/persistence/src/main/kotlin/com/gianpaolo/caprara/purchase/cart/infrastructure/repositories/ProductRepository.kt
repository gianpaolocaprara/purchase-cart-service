package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.infrastructure.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<ProductEntity, Int>