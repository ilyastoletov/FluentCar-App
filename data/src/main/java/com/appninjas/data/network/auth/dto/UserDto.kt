package com.appninjas.data.network.auth.dto

import com.appninjas.domain.model.Offer

data class UserDto(
    val phone: String,
    val email: String,
    val name: String,
    val password: String,
    val offers: List<Offer> = listOf(),
    val responded: List<Int> = listOf()
)