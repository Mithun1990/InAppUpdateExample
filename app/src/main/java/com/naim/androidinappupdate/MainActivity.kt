package com.naim.androidinappupdate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.CallSuper
import com.naim.androidinappupdate.appUpdate.AppUpdate
import com.naim.androidinappupdate.appUpdate.AppUpdateImpl
import com.naim.androidinappupdate.appUpdate.InAppUpdateManager
import com.naim.androidinappupdate.appUpdate.UpdateConstants
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inAppUpdate = InAppUpdateManager(WeakReference(this))
        val appUpdate = AppUpdateImpl(inAppUpdate)
        appUpdate.checkUpdate()
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
}