package com.appninjas.fluentcar.presentation.di

import com.appninjas.fluentcar.presentation.screens.login.LoginViewModel
import com.appninjas.fluentcar.presentation.screens.map.MapViewModel
import com.appninjas.fluentcar.presentation.screens.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MapViewModel(
            geocodeCoordinatesToAdressUseCase = get(),
            reverseGeocodeUseCase = get()
        )
    }

    viewModel {
        RegistrationViewModel(
            registrationUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

}