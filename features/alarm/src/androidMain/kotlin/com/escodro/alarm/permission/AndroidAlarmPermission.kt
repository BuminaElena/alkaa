package com.escodro.alarm.permission

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import com.escodro.alarmapi.AlarmPermission

internal class AndroidAlarmPermission(
    private val screenNavigator: ScreenNavigator,
    private val permissionChecker: PermissionChecker,
    private val sdkVersion: SdkVersion,
) : AlarmPermission {

    @SuppressLint("NewApi")
    override fun hasExactAlarmPermission(): Boolean =
        if (sdkVersion.currentVersion >= Build.VERSION_CODES.S) {
            permissionChecker.canScheduleExactAlarms()
        } else {
            true
        }

    @SuppressLint("NewApi")
    override fun hasNotificationPermission(): Boolean =
        if (sdkVersion.currentVersion >= Build.VERSION_CODES.TIRAMISU) {
            permissionChecker.checkPermission(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            true
        }

    override fun openExactAlarmPermissionScreen() {
        screenNavigator.openExactAlarmPermissionScreen()
    }

    override fun openAppSettings() {
        screenNavigator.openAppSettings()
    }
}
