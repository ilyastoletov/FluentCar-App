package com.appninjas.fluentcar.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.User
import com.appninjas.domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationUseCase: RegisterUseCase) : ViewModel() {

    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            registrationUseCase.invoke(user, onSuccess, onFailure)
        }
    }

}