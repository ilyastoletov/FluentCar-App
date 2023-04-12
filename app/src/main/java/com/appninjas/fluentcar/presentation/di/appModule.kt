package com.appninjas.fluentcar.presentation.di

import com.appninjas.fluentcar.presentation.screens.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MapViewModel(get(), get())
    }
}