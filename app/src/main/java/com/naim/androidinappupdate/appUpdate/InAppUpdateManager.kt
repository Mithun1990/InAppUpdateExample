package com.naim.androidinappupdate.appUpdate

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.tasks.Task
import java.lang.ref.WeakReference

class InAppUpdateManager constructor(private val context: WeakReference<Activity>) : IAppUpdate {
    private var _appUpdateManagerFactory: AppUpdateManager? = null
    private var _appUpdateInfo: Task<AppUpdateInfo>? = null

    init {
        context.get()?.let {
            _appUpdateManagerFactory = AppUpdateManagerFactory.create(it)
            _appUpdateInfo = _appUpdateManagerFactory?.appUpdateInfo
        }
    }

    override fun onImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        getActivity()?.let {
            _appUpdateManagerFactory?.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                it,
                UpdateConstants.RESULT_CODE
            )
        }
    }

    override fun onFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        getActivity()?.let {
            appUpdateManagerFactory?.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.FLEXIBLE,
                it,
                UpdateConstants.RESULT_CODE
            )
        }
    }

    override fun onDownloadInProgress() {
        TODO("Not yet implemented")
    }

    override fun onResumeUpdate(appUpdateInfo: AppUpdateInfo) {
        getActivity()?.let {
            _appUpdateManagerFactory?.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                it,
                UpdateConstants.RESULT_CODE
            )
        }
    }

    override var appUpdateManagerFactory: AppUpdateManager? = _appUpdateManagerFactory

    override var appUpdateInfoTaskInfo: Task<AppUpdateInfo>? = _appUpdateInfo

    private fun getActivity(): Activity? {
        return context.get()
    }

}