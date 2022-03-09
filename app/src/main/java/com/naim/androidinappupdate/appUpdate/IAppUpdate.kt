package com.naim.androidinappupdate.appUpdate

interface IAppUpdate {
    fun onImmediateUpdate()
    fun onFlexibleUpdate()
    fun onDownloadInProgress()
    fun onResumeUpdate()
}