package com.appninjas.fluentcar.presentation.di

import com.appninjas.data.network.auth.mapper.UserMapper
import com.appninjas.data.network.auth.service.AuthApiService
import com.appninjas.data.network.geocode.service.GeocodeApiService
import com.appninjas.data.repository.AuthRepoImpl
import com.appninjas.data.repository.GeocodeRepoImpl
import com.appninjas.domain.repository.AuthRepository
import com.appninjas.domain.repository.GeocodeRepository
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

    single<AuthApiService> {
        provideAuthService(retrofit = get())
    }

    single<GeocodeRepository> {
        GeocodeRepoImpl(
            apiService = get()
        )
    }

    single<AuthRepository> {
        AuthRepoImpl(
            authApiService = get(),
            firebaseAuth = FirebaseAuth.getInstance(),
            mapper = UserMapper()
        )
    }

}

fun provideGeocodeService(retrofit: Retrofit): GeocodeApiService = retrofit.create(GeocodeApiService::class.java)

fun provideAuthService(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)