package com.appninjas.fluentcar.presentation.application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
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
        createNofiticationsChannel()
    }

    private fun createNofiticationsChannel() {
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val id = "main_ch_1"
        val mChannel = NotificationChannel(id, "MainChannelForTrueMans", NotificationManager.IMPORTANCE_HIGH)
        mChannel.description = "Just a channel for true mans"
        mChannel.enableLights(true)
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        mNotificationManager.createNotificationChannel(mChannel)
    }
}