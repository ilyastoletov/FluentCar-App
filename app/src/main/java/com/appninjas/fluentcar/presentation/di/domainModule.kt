package com.appninjas.fluentcar.presentation.di

import com.appninjas.domain.usecase.GeocodeCoordinatesToAdressUseCase
import com.appninjas.domain.usecase.ReverseGeocodeUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GeocodeCoordinatesToAdressUseCase(repository = get())
    }

    factory {
        ReverseGeocodeUseCase(repository = get())
    }
}