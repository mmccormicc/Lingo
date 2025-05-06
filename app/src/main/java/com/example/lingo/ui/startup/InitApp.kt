package com.example.lingo.ui.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.lingo.utils.DeviceIdManager

@Composable
fun InitApp(
    content: @Composable () -> Unit
) {
    // Getting current app context
    val context = LocalContext.current
    // Getting device ID
    remember {
        DeviceIdManager.getOrCreateDeviceId(context)
    }

    println(DeviceIdManager.getOrCreateDeviceId(context))

    content()
}