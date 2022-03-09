package com.naim.androidinappupdate.appUpdate

import android.content.Context
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class AppUpdateImpl(context: Context, iAppUpdate: IAppUpdate) :
    AppUpdate(context, iAppUpdate) {
    override fun onUpdate() {
        appUpdateInfoTaskInfo?.addOnSuccessListener { appUpdateInfo ->
            when {
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {

                }
            }
        }
    }
}