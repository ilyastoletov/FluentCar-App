package com.appninjas.domain.repository

import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials

interface AuthRepository {
    suspend fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit)
    suspend fun loginUser(userCredentials: UserCredentials, onSuccess: () -> Unit, onFailure: () -> Unit)
}