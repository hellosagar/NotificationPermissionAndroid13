package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessanging: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FirebaseMessanging", "onNewToken $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FirebaseMessanging", "From: ${message.from}")

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FirebaseMessanging", "From: ${message.from}")

        // Check if message contains a data payload.
        if (message.data.isNotEmpty()) {
            Log.d("FirebaseMessanging", "Message data payload: ${message.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
            } else {
                // Handle message within 10 seconds
//                handleNow()
            }
        }

        // Check if message contains a notification payload.
        message.notification?.let {
            Log.d("FirebaseMessanging", "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        sendNotification(applicationContext)


//        when {
//            ContextCompat.checkSelfPermission(
//                this, Manifest.permission.POST_NOTIFICATIONS
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                // You can use the API that requires the permission.
//                Log.e(MainActivity.TAG, "onCreate: PERMISSION GRANTED")
//                sendNotification(this)
//            }
//            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
//                sendNotification(this)
//
//                Toast.makeText(applicationContext, "Notification received", Toast.LENGTH_SHORT).show()
//                //  Toast.makeText(this, "NOT ALLOWED", Toast.LENGTH_SHORT).show()
//            }
//            else -> {
//                Log.e(MainActivity.TAG, "onCreate: ask for permissions")
//                // You can directly ask for the permission.
//                // The registered ActivityResultCallback gets the result of this request.
//                //  if (Build.VERSION.SDK_INT >= 33) {
//                //   Log.e(TAG, "onCreate: 33" )
////                requestPermissionLauncher.launch(
////                    Manifest.permission.POST_NOTIFICATIONS
////                )
//                // }
//            }
//        }



    }
}