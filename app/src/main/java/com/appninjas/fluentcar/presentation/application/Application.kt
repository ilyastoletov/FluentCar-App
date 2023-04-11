package com.appninjas.fluentcar.presentation.application

import com.yandex.mapkit.MapKitFactory

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("8d11d5d6-961e-4621-8d9d-3bb17f0eba64")
    }
}