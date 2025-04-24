package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Product
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<Product, Int>