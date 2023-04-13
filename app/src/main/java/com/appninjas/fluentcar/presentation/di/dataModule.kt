package com.appninjas.fluentcar.presentation.di

import com.appninjas.data.network.auth.mapper.UserMapper
import com.appninjas.data.network.auth.service.AuthApiService
import com.appninjas.data.network.geocode.service.GeocodeApiService
import com.appninjas.data.network.offer.mapper.OfferMapper
import com.appninjas.data.network.offer.service.OfferApiClient
import com.appninjas.data.repository.AuthRepoImpl
import com.appninjas.data.repository.GeocodeRepoImpl
import com.appninjas.data.repository.OfferRepoImpl
import com.appninjas.domain.repository.AuthRepository
import com.appninjas.domain.repository.GeocodeRepository
import com.appninjas.domain.repository.OfferRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://95.163.234.235:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GeocodeApiService> {
        provideGeocodeService(retrofit = get())
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<AuthApiService> {
        provideAuthService(retrofit = get())
    }

    single<OfferApiClient> {
        provideOfferApiClient(retrofit = get())
    }

    single<GeocodeRepository> {
        GeocodeRepoImpl(
            apiService = get()
        )
    }

    single<AuthRepository> {
        AuthRepoImpl(
            authApiService = get(),
            firebaseAuth = get(),
            mapper = UserMapper()
        )
    }

    single<OfferRepository> {
        OfferRepoImpl(
            offerApiClient = get(),
            offerMapper = OfferMapper(),
            firebaseAuth = get(),
            userMapper = UserMapper()
        )
    }

}

fun provideGeocodeService(retrofit: Retrofit): GeocodeApiService = retrofit.create(GeocodeApiService::class.java)
fun provideAuthService(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)
fun provideOfferApiClient(retrofit: Retrofit): OfferApiClient = retrofit.create(OfferApiClient::class.java)