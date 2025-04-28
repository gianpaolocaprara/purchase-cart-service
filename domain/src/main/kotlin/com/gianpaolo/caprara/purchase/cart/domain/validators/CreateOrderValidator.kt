package com.gianpaolo.caprara.purchase.cart.domain.validators

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CreateOrderValidator : Validator<Order> {

    private val logger: Logger = LoggerFactory.getLogger(CreateOrderValidator::class.java)

    override fun validate(value: Order) {
        if (value.items.isEmpty()) {
            logger.warn("Order must contain at least one item.")
            throw InvalidParameterException("Order must contain at least one item.")
        }
        if (value.items.any { it.quantity <= 0 }) {
            logger.warn("Order must contain only positive quantities.")
            throw InvalidParameterException("Order must contain only positive quantities.")
        }
    }
}