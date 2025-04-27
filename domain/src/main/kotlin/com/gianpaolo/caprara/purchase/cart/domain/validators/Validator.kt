package com.gianpaolo.caprara.purchase.cart.domain.validators

interface Validator<T : Any> {
    fun validate(value: T)
}