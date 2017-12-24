package org.pretorh.example.firebasetest

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseTokenService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "token refreshed: $token")
    }

    companion object {
        private val TAG = "FirebaseService"
    }
}
