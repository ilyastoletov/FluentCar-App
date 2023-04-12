package com.appninjas.domain.usecase

import com.appninjas.domain.model.User
import com.appninjas.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend fun invoke(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) = repository.registerUser(user, onSuccess, onFailure)
}