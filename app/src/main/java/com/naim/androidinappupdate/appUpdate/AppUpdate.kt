package com.naim.androidinappupdate.appUpdate

import android.app.Activity
import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.tasks.Task
import java.lang.ref.WeakReference

abstract class AppUpdate constructor(
    private val iAppUpdate: IAppUpdate
) {
    abstract fun checkUpdate()
}