package com.appninjas.data.network.auth.service

import com.appninjas.data.network.auth.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/createUser")
    suspend fun addUserToDatabase(@Body userBody: UserDto)

}