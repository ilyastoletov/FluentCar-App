package com.appninjas.domain.repository

import com.appninjas.domain.model.Geocode

interface GeocodeRepository {
    suspend fun convertCoordinatesToAddress(lat: Double, lon: Double): Geocode
    suspend fun convertAddressToCoordinates(address: String): Geocode
}