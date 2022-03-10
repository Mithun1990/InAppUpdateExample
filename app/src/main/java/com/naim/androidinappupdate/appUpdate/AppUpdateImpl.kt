package com.naim.androidinappupdate.appUpdate

import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class AppUpdateImpl(
    private val
    iAppUpdate: IAppUpdate
) :
    AppUpdate(iAppUpdate) {
    private var listener: InstallStateUpdatedListener? = null

    init {
        listener = InstallStateUpdatedListener { installState ->
            when {
                installState.installStatus() == InstallStatus.DOWNLOADING -> {
                    val downloadedBytes = installState.bytesDownloaded()
                    val totalBytes = installState.totalBytesToDownload()
                    iAppUpdate.onDownloadInProgress(downloadedBytes, totalBytes)
                }
                installState.installStatus() == InstallStatus.DOWNLOADED -> {
                    iAppUpdate.onDownloadCompleted()
                }
            }
        }
    }

    override fun checkUpdate() {
        iAppUpdate.appUpdateInfoTaskInfo?.addOnSuccessListener { appUpdateInfo ->
            when {
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                    iAppUpdate.onImmediateUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                    /*&& (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= 5*/ -> {
                    iAppUpdate.onFlexibleUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    iAppUpdate.onResumeUpdate(appUpdateInfo)
                }
            }
        }
    }

    override fun registerDownloadListener() {
        listener?.let {
            iAppUpdate.appUpdateManagerFactory?.registerListener(it)
        }
    }

    override fun unregisterDownloadListener() {
        listener?.let {
            iAppUpdate.appUpdateManagerFactory?.unregisterListener(it)
        }
    }
}