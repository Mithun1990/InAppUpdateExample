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
        println("App update impl called")
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
                installState.installStatus() == InstallStatus.INSTALLED -> {
                    listener?.let { iAppUpdate.appUpdateManagerFactory?.unregisterListener(it) }
                }
            }
        }
    }

    override fun checkUpdate(isForceUpdate: Boolean) {
        println("Already App update impl called")
        iAppUpdate.appUpdateInfoTaskInfo?.addOnSuccessListener { appUpdateInfo ->
            println("Already App update impl called appUpdateInfo")
            when {
                /*
                isForceUpdate is used explicitly as google play did not
                include any option to distinguish immediate or flexible release
                by adding isForceUpdate this code will be worked if google play add any option
                 */
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) || isForceUpdate) -> {
                    iAppUpdate.onImmediateUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) || !isForceUpdate)
                    /*&& (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= 5*/ -> {
                    iAppUpdate.onFlexibleUpdate(appUpdateInfo)
                }
                appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    iAppUpdate.onResumeUpdate(appUpdateInfo)
                }
                else -> {
                    println("Already App update impl called")
                }
            }
        }
        iAppUpdate.appUpdateInfoTaskInfo?.addOnFailureListener {
            iAppUpdate.onException(it)
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