package com.appninjas.domain.usecase

import com.appninjas.domain.model.Offer
import com.appninjas.domain.repository.OfferRepository

class SearchOfferUseCase(private val repository: OfferRepository) {
    suspend fun invoke(query: String, status: Boolean): List<Offer> = repository.searchOffers(query, status)
}