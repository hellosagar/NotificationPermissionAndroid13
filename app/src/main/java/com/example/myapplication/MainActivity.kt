package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

/**
 * @author Nav Singh
 */
class MainActivity : AppCompatActivity() {
    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            sendNotification(this)

            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            sendNotification(this)

            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.analytics

        val message = intent?.getStringExtra(NOTIFICATION_MESSAGE_TAG)
        findViewById<TextView>(R.id.tv_message).text = message
        findViewById<Button>(R.id.click).setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    Log.e(TAG, "onCreate: PERMISSION GRANTED")
                    sendNotification(this)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    sendNotification(this)

                    Snackbar.make(
                        findViewById(R.id.parent_layout),
                        "Notification blocked",
                        Snackbar.LENGTH_LONG
                    ).setAction("Settings") {
                        // Responds to click on the action
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val uri: Uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }.show()
                    //  Toast.makeText(this, "NOT ALLOWED", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.e(TAG, "onCreate: ask for permissions")
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                  //  if (Build.VERSION.SDK_INT >= 33) {
                     //   Log.e(TAG, "onCreate: 33" )
                        requestPermissionLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                   // }
                }
            }
        }
        
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            if (!TextUtils.isEmpty(it)) {
                Log.d(TAG, "retrieve token successful : " + it);
            } else{
                Log.w(TAG, "token should not be null...");
            }
        }.addOnFailureListener {
            Log.w(TAG, "token fail");
        }.addOnCanceledListener {
            Log.w(TAG, "token cancel");
        }.addOnCompleteListener {
            Log.v(TAG, "This is the token : " + it.result)
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val NOTIFICATION_MESSAGE_TAG = "message from notification"
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            putExtra(
                NOTIFICATION_MESSAGE_TAG, "Hi ☕\uD83C\uDF77\uD83C\uDF70"
            )
        }
    }
}
