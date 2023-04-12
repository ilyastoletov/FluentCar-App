package com.appninjas.domain.usecase

import com.appninjas.domain.model.Geocode
import com.appninjas.domain.repository.GeocodeRepository

class ReverseGeocodeUseCase(private val repository: GeocodeRepository) {
    suspend fun invoke(address: String): Geocode = repository.convertAddressToCoordinates(address)
}