package com.gianpaolo.caprara.purchase.cart.infrastructure.repositories

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Int>