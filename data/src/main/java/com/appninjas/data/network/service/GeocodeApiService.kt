package com.appninjas.data.network.service

import com.appninjas.data.network.dto.GeocodeCityDto
import com.appninjas.data.network.dto.GeocodeCoordsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeApiService {

    @GET("/geocodeCoords")
    suspend fun getAddressByCoords(@Query("latitude") lat: Double, @Query("longitude") lon: Double): GeocodeCityDto

    @GET("/reverseGeocode")
    suspend fun getCordsByAddress(@Query("adress_string") address: String): GeocodeCoordsDto

}