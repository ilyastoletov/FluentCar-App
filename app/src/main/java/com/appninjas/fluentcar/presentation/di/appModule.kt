package com.appninjas.fluentcar.presentation.di

import com.appninjas.domain.usecase.SearchOfferUseCase
import com.appninjas.fluentcar.presentation.screens.login.LoginViewModel
import com.appninjas.fluentcar.presentation.screens.map.MapViewModel
import com.appninjas.fluentcar.presentation.screens.my_offers.MyOffersViewModel
import com.appninjas.fluentcar.presentation.screens.registration.RegistrationViewModel
import com.appninjas.fluentcar.presentation.screens.search.SearhViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MapViewModel(
            geocodeCoordinatesToAdressUseCase = get(),
            reverseGeocodeUseCase = get(),
            createOfferUseCase = get()
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

    viewModel {
        MyOffersViewModel(
            myOffersUseCase = get()
        )
    }

    viewModel {
        SearhViewModel(searchOfferUseCase = get(), respontOfferUseCase = get())
    }

}