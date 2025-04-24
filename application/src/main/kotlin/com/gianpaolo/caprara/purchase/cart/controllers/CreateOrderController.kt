package com.gianpaolo.caprara.purchase.cart.controllers

import com.gianpaolo.caprara.purchase.cart.domain.models.Order
import com.gianpaolo.caprara.purchase.cart.domain.usecases.CreateOrderUseCase
import com.gianpaolo.caprara.purchase.cart.dtos.requests.CreateOrderDTO
import com.gianpaolo.caprara.purchase.cart.dtos.requests.toModel
import com.gianpaolo.caprara.purchase.cart.dtos.responses.CreateOrderDTOResponse
import com.gianpaolo.caprara.purchase.cart.dtos.responses.OrderItemDTOResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping(ordersPath)
class CreateOrderController(
    private val createOrderUseCase: CreateOrderUseCase
) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody createOrderDTO: CreateOrderDTO): ResponseEntity<*> {
        val createdOrder: Order = createOrderUseCase.apply(createOrderDTO.toModel())
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.id!!)
                .toUri()
        ).body(
            CreateOrderDTOResponse(
                id = createdOrder.id!!,
                items = createdOrder.items.map {
                    OrderItemDTOResponse(
                        id = it.product.id,
                        quantity = it.quantity,
                        price = it.product.price!!,
                        vat = it.product.vat!!
                    )
                },
                vat = createdOrder.vat!!,
                price = createdOrder.price!!
            )
        )
    }
}