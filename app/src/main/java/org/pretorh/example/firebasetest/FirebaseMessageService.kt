package org.pretorh.example.firebasetest

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
    }

    companion object {
        val TAG = "FirebaseMessageService"
    }
}
