package com.appninjas.domain.usecase

import com.appninjas.domain.model.Geocode
import com.appninjas.domain.repository.GeocodeRepository

class GeocodeCoordinatesToAdressUseCase(private val repository: GeocodeRepository) {
    suspend fun invoke(lat: Double, lon: Double): Geocode = repository.convertCoordinatesToAddress(lat, lon)
}