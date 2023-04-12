package com.appninjas.domain.repository

import com.appninjas.domain.model.Offer

interface OfferRepository {
    suspend fun createOffer(offer: Offer)
    suspend fun getUserOffers(): List<Offer>
}