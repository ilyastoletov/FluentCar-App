package com.appninjas.data.network.dto

import com.google.gson.annotations.SerializedName

data class GeocodeCoordsDto(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)
