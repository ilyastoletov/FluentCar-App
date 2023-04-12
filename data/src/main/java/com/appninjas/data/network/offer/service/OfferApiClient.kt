package com.appninjas.data.network.offer.service

import com.appninjas.data.network.auth.dto.UserDto
import com.appninjas.data.network.offer.dto.OfferDto
import com.appninjas.domain.model.Offer
import com.appninjas.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OfferApiClient {

    @POST("/createOffer")
    suspend fun createOffer(@Body offerBody: OfferDto)

    @GET("/getUserData")
    suspend fun getCurrentUserData(@Query("user_email") userMail: String): UserDto

}