package com.naim.androidinappupdate.appUpdate

import android.content.Context
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class AppUpdateImpl(
    private val
    iAppUpdate: IAppUpdate
) :
    AppUpdate(iAppUpdate) {
    override fun checkUpdate() {
        iAppUpdate.appUpdateInfoTaskInfo?.addOnSuccessListener { appUpdateInfo ->
            when {
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                    iAppUpdate.onImmediateUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                        && (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= 5 -> {
                    iAppUpdate.onFlexibleUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    iAppUpdate.onResumeUpdate(appUpdateInfo)
                }
            }
        }
    }
}