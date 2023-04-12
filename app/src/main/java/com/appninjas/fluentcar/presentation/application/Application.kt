package com.appninjas.fluentcar.presentation.application

import com.appninjas.fluentcar.presentation.di.appModule
import com.appninjas.fluentcar.presentation.di.dataModule
import com.appninjas.fluentcar.presentation.di.domainModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("8d11d5d6-961e-4621-8d9d-3bb17f0eba64")
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}