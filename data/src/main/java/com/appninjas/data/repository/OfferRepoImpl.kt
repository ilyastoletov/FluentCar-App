package com.appninjas.data.repository

import com.appninjas.data.network.auth.mapper.UserMapper
import com.appninjas.data.network.offer.mapper.OfferMapper
import com.appninjas.data.network.offer.service.OfferApiClient
import com.appninjas.domain.model.Offer
import com.appninjas.domain.repository.OfferRepository
import com.google.firebase.auth.FirebaseAuth

class OfferRepoImpl(private val offerApiClient: OfferApiClient,
                    private val offerMapper: OfferMapper,
                    private val firebaseAuth: FirebaseAuth, private val userMapper: UserMapper) : OfferRepository {

    override suspend fun createOffer(offer: Offer) {
        val user = offerApiClient.getCurrentUserData(firebaseAuth.currentUser!!.email!!)
        offerApiClient.createOffer(offerMapper.offerToOfferDto(offer, userMapper.userDtoToUser(user)))
    }

    override suspend fun getUserOffers(): List<Offer> {
        val userData = offerApiClient.getCurrentUserData(firebaseAuth.currentUser!!.email!!)
        return userData.offers
    }

}