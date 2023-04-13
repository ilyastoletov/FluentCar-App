package com.appninjas.domain.usecase

import com.appninjas.domain.model.Offer
import com.appninjas.domain.repository.OfferRepository

class GetMyOffersUseCase(private val repository: OfferRepository) {
    suspend fun invoke(): List<Offer> = repository.getUserOffers()
}