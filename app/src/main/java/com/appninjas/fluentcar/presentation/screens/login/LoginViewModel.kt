package com.appninjas.fluentcar.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    fun loginUser(userCredentials: UserCredentials, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.invoke(userCredentials, onSuccess, onFailure)
        }
    }

}