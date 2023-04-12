package com.appninjas.domain.model

data class Offer(
    var price: Number,
    var route: String,
    var status: Boolean,
    var maxPassengers: Int,
    var responseCount: Int = 0
)
