package com.appninjas.data.network.offer.dto

import com.appninjas.domain.model.UserInfo

data class OfferDto(
    val offer_id: Number,
    val price: Number,
    val route: String,
    val maxPassenger: Number,
    val status: Boolean,
    val responses: Number = 0,
    val name: String,
    val phone: String,
    val userEmail: String,
    var respondents: List<UserInfo> = listOf()
)