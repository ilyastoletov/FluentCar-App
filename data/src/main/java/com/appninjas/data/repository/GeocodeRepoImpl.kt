package com.appninjas.data.repository

import android.util.Log
import com.appninjas.data.network.geocode.service.GeocodeApiService
import com.appninjas.domain.model.Geocode
import com.appninjas.domain.repository.GeocodeRepository
import retrofit2.HttpException

class GeocodeRepoImpl(private val apiService: GeocodeApiService) : GeocodeRepository {

    override suspend fun convertAddressToCoordinates(address: String): Geocode {
        /*return try {
            val apiResponse = apiService.getCordsByAddress(address)
            Geocode(
                latitude = apiResponse.latitude.toDouble(),
                longitude = apiResponse.longitude.toDouble()
            )
        } catch (e: HttpException) {
            Log.e("MAP", "Got HTTP exception")
            Geocode()
        }*/
        val apiResponse = apiService.getCordsByAddress(address)
        return Geocode(
            latitude = apiResponse.latitude.toDouble(),
            longitude = apiResponse.longitude.toDouble()
        )
    }

    override suspend fun convertCoordinatesToAddress(lat: Double, lon: Double): Geocode {
        val apiResponse = apiService.getAddressByCoords(lat, lon)
        return Geocode(address = apiResponse.addressString)
    }

}