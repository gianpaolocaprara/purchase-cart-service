package com.gianpaolo.caprara.purchase.cart.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

val objectMapper = ObjectMapper().registerKotlinModule()

fun MockMvc.post(path: String, entity: Any) = this.post(path) {
    contentType = MediaType.APPLICATION_JSON
    content = objectMapper.writeValueAsString(entity)
}