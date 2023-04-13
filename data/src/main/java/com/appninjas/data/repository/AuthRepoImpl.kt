package com.appninjas.data.repository

import android.util.Log
import com.appninjas.data.network.auth.mapper.UserMapper
import com.appninjas.data.network.auth.service.AuthApiService
import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(private val firebaseAuth: FirebaseAuth,
                   private val authApiService: AuthApiService,
                   private val mapper: UserMapper) : AuthRepository {

    override suspend fun loginUser(userCredentials: UserCredentials, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(userCredentials.email, userCredentials.password).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    override suspend fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        authApiService.addUserToDatabase(mapper.userToUserDto(user))
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                onFailure()
                Log.d("EX", it.exception.toString())
            }
        }
    }

}