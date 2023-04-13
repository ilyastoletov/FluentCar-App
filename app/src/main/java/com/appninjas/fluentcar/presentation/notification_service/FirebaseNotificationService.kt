package com.appninjas.fluentcar.presentation.notification_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.appninjas.fluentcar.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random


class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    private fun sendNotification(message: RemoteMessage) {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d("NOTIF", "${message.data["title"]} : ${message.data["body"]}")
        val notification = NotificationCompat.Builder(applicationContext, "")
            .setSmallIcon(R.drawable.baseline_location_on_24)
            .setContentTitle(message.notification!!.title)
            .setContentText(message.notification!!.body)
            .setChannelId("main_ch_1")
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

}