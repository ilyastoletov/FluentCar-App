package com.appninjas.data.network.offer.mapper

import com.appninjas.data.network.offer.dto.OfferDto
import com.appninjas.domain.model.Offer
import com.appninjas.domain.model.User
import kotlin.random.Random

class OfferMapper {

    fun offerToOfferDto(offer: Offer, user: User): OfferDto = OfferDto(
        offer_id = Random.nextInt(),
        price = offer.price,
        route = offer.route.replace("Россия, ", ""),
        maxPassenger = offer.maxPassengers,
        status = offer.status,
        name = user.name,
        phone = user.phone,
        userEmail = user.email
    )

    fun offerDtoListToOfferList(dtoList: List<OfferDto>): List<Offer> = dtoList.map { dto -> Offer(
        price = dto.price,
        route = dto.route,
        status = dto.status,
        maxPassengers = dto.maxPassenger.toInt(),
        responseCount = dto.responses.toInt(),
        respondentsList = dto.respondents,
        driverName = dto.name,
        driverPhone = dto.phone
    ) }

}