package com.appninjas.data.repository

import android.util.Log
import com.appninjas.data.network.auth.mapper.UserMapper
import com.appninjas.data.network.offer.dto.OfferDto
import com.appninjas.data.network.offer.mapper.OfferMapper
import com.appninjas.data.network.offer.service.OfferApiClient
import com.appninjas.domain.model.Offer
import com.appninjas.domain.model.UserInfo
import com.appninjas.domain.repository.OfferRepository
import com.google.firebase.auth.FirebaseAuth

class OfferRepoImpl(private val offerApiClient: OfferApiClient,
                    private val offerMapper: OfferMapper,
                    private val firebaseAuth: FirebaseAuth, private val userMapper: UserMapper) : OfferRepository {

    override suspend fun createOffer(offer: Offer) {
        val user = offerApiClient.getCurrentUserData(firebaseAuth.currentUser!!.email!!)
        val mappedOffer = offerMapper.offerToOfferDto(offer, userMapper.userDtoToUser(user))
        offerApiClient.createOffer(mappedOffer)
    }

    override suspend fun getUserOffers(): List<Offer> {
        val userData = offerApiClient.getCurrentUserData(firebaseAuth.currentUser!!.email!!)
        val userOffers: ArrayList<OfferDto> = arrayListOf()
        Log.d("OFFERS", userOffers.toString())
        val allOffers = offerApiClient.getAllOffers()
        //val respondentList: ArrayList<UserInfo> = arrayListOf()
        for (offer in allOffers) {
            if (offer.phone == userData.phone) {
                userOffers.add(offer)
            }
        }
        Log.d("OFFERS", userOffers.toString())
        return offerMapper.offerDtoListToOfferList(userOffers)
    }

    override suspend fun searchOffers(query: String, status: Boolean): List<Offer> {
        val allOffers = offerMapper.offerDtoListToOfferList(offerApiClient.getAllOffers())
        val sortedOffers: ArrayList<Offer> = arrayListOf()

        for (offer in allOffers) {
            if (offer.route.contains(query)) {
                if (offer.status == status) {
                    sortedOffers.add(offer)
                }
            }
        }
        return sortedOffers
    }

    override suspend fun offerResponded(offer: Offer) {
        val userData = offerApiClient.getCurrentUserData(firebaseAuth.currentUser!!.email!!)
        offerApiClient.respondOffer(offer.route, userData)
    }

}