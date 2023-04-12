package com.appninjas.domain.usecase

import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun invoke(userCredentials: UserCredentials, onSuccess: () -> Unit, onFailure: () -> Unit) = repository.loginUser(userCredentials, onSuccess, onFailure)
}