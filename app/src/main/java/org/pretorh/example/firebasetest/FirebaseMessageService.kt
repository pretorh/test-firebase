package org.pretorh.example.firebasetest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessageService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage == null) {
            Log.d(TAG, "null remoteMessage")
            return
        }

        Log.d(TAG, "from: " + remoteMessage.from)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "message payload: " + remoteMessage.data)
        }

        if (remoteMessage.notification != null) {
            Log.d(TAG, "message body: " + remoteMessage.notification?.body)
        }

        createNotification(remoteMessage)
    }

    private fun createNotification(remoteMessage: RemoteMessage) {
        createNotification(remoteMessage.notification)
    }

    private fun createNotification(notification: RemoteMessage.Notification?) {
        val defaultNotificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val pendingIntent = PendingIntent.getActivity(this, 0, buildIntent(),
                PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notification?.title)
                .setContentText(notification?.body)
                .setAutoCancel(true)
                .setSound(defaultNotificationRingtone)
                .setContentIntent(pendingIntent)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(0, builder.build())
    }

    private fun buildIntent(): Intent {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    companion object {
        val TAG = "FirebaseMessageService"
        val CHANNEL_ID = "MAIN"
    }
}
