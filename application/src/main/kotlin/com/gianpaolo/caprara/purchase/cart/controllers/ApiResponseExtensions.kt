package com.gianpaolo.caprara.purchase.cart.controllers

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

fun location(id: Int) = ServletUriComponentsBuilder
    .fromCurrentRequest()
    .path("/{id}")
    .buildAndExpand(id)
    .toUri()