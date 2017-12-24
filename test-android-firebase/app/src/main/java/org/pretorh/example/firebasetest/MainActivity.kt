package org.pretorh.example.firebasetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "current token: ${FirebaseInstanceId.getInstance()?.token}")
    }

    companion object {
        val TAG = "MainActivity"
    }
}
