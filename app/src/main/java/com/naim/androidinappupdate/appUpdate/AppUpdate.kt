package com.naim.androidinappupdate.appUpdate

abstract class AppUpdate constructor(
    private val iAppUpdate: IAppUpdate
) {
    abstract fun checkUpdate(isForceUpdate: Boolean)
    abstract fun registerDownloadListener()
    abstract fun unregisterDownloadListener()
}