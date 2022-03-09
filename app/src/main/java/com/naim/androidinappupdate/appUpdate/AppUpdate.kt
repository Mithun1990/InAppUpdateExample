package com.naim.androidinappupdate.appUpdate

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.tasks.Task

abstract class AppUpdate constructor(
    private val context: Context,
    private val iAppUpdate: IAppUpdate
) {

    var appUpdateManagerFactory: AppUpdateManager? = null
    var appUpdateInfoTaskInfo: Task<AppUpdateInfo>? = null

    init {
        appUpdateManagerFactory = AppUpdateManagerFactory.create(context)
        appUpdateInfoTaskInfo = appUpdateManagerFactory?.appUpdateInfo
    }

    abstract fun onUpdate()
}