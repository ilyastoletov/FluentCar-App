package com.appninjas.data.network.dto

import com.google.gson.annotations.SerializedName

data class GeocodeCityDto(
    @SerializedName("city_name")
    val addressString: String
)
