package com.naim.androidinappupdate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naim.androidinappupdate.appUpdate.AppUpdate
import com.naim.androidinappupdate.appUpdate.AppUpdateImpl
import com.naim.androidinappupdate.appUpdate.InAppUpdateManager
import com.naim.androidinappupdate.appUpdate.UpdateConstants
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    var appUpdate: AppUpdate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inAppUpdate = InAppUpdateManager(WeakReference(this))
        appUpdate = AppUpdateImpl(inAppUpdate)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UpdateConstants.RESULT_CODE) {
            if (resultCode != RESULT_OK) {
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