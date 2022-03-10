package com.naim.androidinappupdate

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.naim.androidinappupdate.appUpdate.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    var appUpdate: AppUpdate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inAppUpdate = InAppUpdateManager(WeakReference(this)) {
            when (it) {
                is AppUpdateEvent.AppUpdateException -> {
                    println("Already App update impl called appUpdateInfo ${it.it.printStackTrace()}")
                }
                is AppUpdateEvent.AppUpdateDownloading -> {
                    val progressBar = findViewById<ProgressBar>(R.id.progress_horizontal)
                    progressBar.progress = it.downloadBytes.toInt()
                    progressBar.max = it.totalBytes.toInt()
                }
                is AppUpdateEvent.AppUpdateComplete -> {

                }
            }
        }
        appUpdate = AppUpdateImpl(inAppUpdate)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UpdateConstants.RESULT_CODE) {
            if (resultCode != RESULT_OK) {
                println("Already App update impl called Failure")
//                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdate?.checkUpdate()
    }

    override fun onStart() {
        super.onStart()
        appUpdate?.registerDownloadListener()
    }

    override fun onStop() {
        super.onStop()
        appUpdate?.unregisterDownloadListener()
    }
}