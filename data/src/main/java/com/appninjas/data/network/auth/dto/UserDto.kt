package com.appninjas.data.network.auth.dto

import com.appninjas.data.network.offer.dto.OfferDto
import com.appninjas.domain.model.Offer

data class UserDto(
    val phone: String,
    val email: String,
    val name: String,
    val password: String,
    val offers: List<OfferDto> = listOf(),
    val responded: List<Int> = listOf()
)