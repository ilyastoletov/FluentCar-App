package com.appninjas.data.network.geocode.dto

import com.google.gson.annotations.SerializedName

data class GeocodeCityDto(
    @SerializedName("city_name")
    val addressString: String
)
