package com.gianpaolo.caprara.purchase.cart.controllers

import com.gianpaolo.caprara.purchase.cart.domain.exceptions.InvalidParameterException
import com.gianpaolo.caprara.purchase.cart.dtos.requests.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {

    companion object {
        const val INVALID_DATA_CODE = "INVALID_DATA"
    }

    @ExceptionHandler(value = [InvalidParameterException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidParameterExceptionHandler(exception: InvalidParameterException): ResponseEntity<ErrorDTO> {
        return ResponseEntity
            .badRequest()
            .body(ErrorDTO(code = INVALID_DATA_CODE, message = exception.message!!))
    }
}