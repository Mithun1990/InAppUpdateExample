package com.naim.androidinappupdate.appUpdate

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.tasks.Task

interface IAppUpdate {
    fun onImmediateUpdate(appUpdateInfo: AppUpdateInfo)
    fun onFlexibleUpdate(appUpdateInfo: AppUpdateInfo)
    fun onDownloadInProgress(downloadedBytes: Long, totalBytes: Long)
    fun onDownloadCompleted()
    fun onResumeUpdate(appUpdateInfo: AppUpdateInfo)
    fun onException(it: Exception)
    var appUpdateManagerFactory: AppUpdateManager?
    var appUpdateInfoTaskInfo: Task<AppUpdateInfo>?
}