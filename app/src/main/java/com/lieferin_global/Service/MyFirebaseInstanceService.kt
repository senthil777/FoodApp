package com.lieferin_global.Service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lieferin_global.Activity.DashBoardActivity
import com.lieferin_global.R
import com.lieferin_global.Activity.DashBoardGrpceryActivity
import com.lieferin_global.Activity.OrderRatingActivity
import com.lieferin_global.Utility.showToast

class MyFirebaseInstanceService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        showNotification(
            remoteMessage.notification!!.title,
            remoteMessage.notification!!.body
        )
        Log.v("Notification"+remoteMessage.notification!!.title,"Value")


    }

    @SuppressLint("ServiceCast")
    private fun showNotification(title: String?, body: String?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        Log.v("Notification"+title,"Value")

        val Notification_channel = "com.lieferin_global"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Notification_channel,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "sdsdsd"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableLights(true)
            notificationManager!!.createNotificationChannel(notificationChannel)
        }

        var activityVariable:String=""
        if(title!!.equals("Order Delivery"))
        {
            activityVariable = "OrderRatingActivity"

            Log.v("Notification1"+title,"Value")

        }else{
            activityVariable = "DashBoardActivity"
        }
        val intent = Intent(this, DashBoardActivity::class.java)
        intent.putExtra("NotificationTitle", title)
        intent.putExtra("NotificationBody", body)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            this@MyFirebaseInstanceService, 0,
            intent, 0
        )
        val notificationBuilder  = NotificationCompat.Builder(this@MyFirebaseInstanceService, Notification_channel)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.logo_lie)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo("Info")
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(contentIntent)
        notificationManager!!.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d("TokenId", "uuuu$s")
    }


}
