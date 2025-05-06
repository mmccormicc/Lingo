package com.example.lingo.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.UUID

object DeviceIdManager {
    private const val DEVICE_ID_KEY = "device_id"
    private const val PREF_FILE_NAME = "secure_prefs"

    fun getOrCreateDeviceId(context: Context): String {
        // Creating master key
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Creating encrypted shared preferences
        val securePrefs = EncryptedSharedPreferences.create(
            context,
            PREF_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Attempt to retrieve device id from secure preferences
        return securePrefs.getString(DEVICE_ID_KEY, null)
        // If id not found, create a random new one
        ?: UUID.randomUUID().toString()
        // Storing new id in secure prefs
        .also { newId ->
        securePrefs.edit().putString(DEVICE_ID_KEY, newId).apply()
        }
    }
}