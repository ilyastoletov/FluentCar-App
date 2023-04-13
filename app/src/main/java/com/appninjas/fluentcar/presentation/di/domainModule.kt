package com.appninjas.fluentcar.presentation.di

import com.appninjas.domain.usecase.*
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

    factory {
        CreateOfferUseCase(repository = get())
    }

    factory {
        GetMyOffersUseCase(repository = get())
    }

    factory {
        SearchOfferUseCase(repository = get())
    }

    factory {
        RespontOfferUseCase(repository = get())
    }

}