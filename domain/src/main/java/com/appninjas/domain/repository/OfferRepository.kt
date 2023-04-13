package com.appninjas.domain.repository

import com.appninjas.domain.model.Offer
import com.appninjas.domain.model.UserCredentials

interface OfferRepository {
    suspend fun createOffer(offer: Offer)
    suspend fun getUserOffers(): List<Offer>
    suspend fun searchOffers(query: String, status: Boolean): List<Offer>
    suspend fun offerResponded(offer: Offer)
}