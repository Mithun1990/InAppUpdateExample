package com.naim.androidinappupdate.appUpdate

sealed class AppUpdateEvent {
    data class AppUpdateException(val it: Exception) : AppUpdateEvent()
    object AppUpdateComplete : AppUpdateEvent()
    data class AppUpdateDownloading(val downloadBytes: Long, val totalBytes: Long) :
        AppUpdateEvent()
}