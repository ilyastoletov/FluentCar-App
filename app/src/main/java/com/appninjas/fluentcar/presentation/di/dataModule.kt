package com.appninjas.fluentcar.presentation.di

import com.appninjas.data.network.service.GeocodeApiService
import com.appninjas.data.repository.GeocodeRepoImpl
import com.appninjas.domain.repository.GeocodeRepository
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

    single<GeocodeRepository> {
        GeocodeRepoImpl(
            apiService = get()
        )
    }

}

fun provideGeocodeService(retrofit: Retrofit): GeocodeApiService = retrofit.create(GeocodeApiService::class.java)