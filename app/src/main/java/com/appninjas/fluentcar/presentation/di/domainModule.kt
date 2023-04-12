package com.appninjas.fluentcar.presentation.di

import com.appninjas.domain.usecase.GeocodeCoordinatesToAdressUseCase
import com.appninjas.domain.usecase.LoginUseCase
import com.appninjas.domain.usecase.RegisterUseCase
import com.appninjas.domain.usecase.ReverseGeocodeUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GeocodeCoordinatesToAdressUseCase(repository = get())
    }

    factory {
        ReverseGeocodeUseCase(repository = get())
    }

    factory {
        RegisterUseCase(repository = get())
    }

    factory {
        LoginUseCase(repository = get())
    }
}