package com.gianpaolo.caprara.purchase.cart.controllers

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.usecases.CreateOrderUseCase
import com.gianpaolo.caprara.purchase.cart.dtos.requests.CreateOrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.toModel
import com.gianpaolo.caprara.purchase.cart.dtos.responses.toCreateOrderDTOResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ordersPath)
class CreateOrderController(
    private val createOrderUseCase: CreateOrderUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(CreateOrderController::class.java)

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody createOrderDTO: CreateOrderDTO): ResponseEntity<*> {
        logger.info("Create a new order.")
        val createdOrder: Order = createOrderUseCase.apply(createOrderDTO.toModel())
        logger.debug("Order created with id: ${createdOrder.id}.")
        return ResponseEntity.created(location(createdOrder.id!!)).body(createdOrder.toCreateOrderDTOResponse())
    }
}