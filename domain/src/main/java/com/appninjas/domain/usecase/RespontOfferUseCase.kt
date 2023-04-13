package com.appninjas.domain.usecase

import com.appninjas.domain.model.Offer
import com.appninjas.domain.repository.OfferRepository

class RespontOfferUseCase(private val repository: OfferRepository) {
    suspend fun invoke(offer: Offer) = repository.offerResponded(offer)
}