package com.escodro.permission.di

import com.escodro.permission.api.IosPermissionController
import com.escodro.permission.api.PermissionController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformPermissionModule = module {
    singleOf(::IosPermissionController) bind PermissionController::class
}
