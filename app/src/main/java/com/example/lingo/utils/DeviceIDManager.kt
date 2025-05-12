package com.example.lingo.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import java.util.UUID

object DeviceIdManager {

    val repository = QuizRepository(RetrofitProvider.quizApiService)

    private const val DEVICE_ID_KEY = "device_id"
    private const val PREF_FILE_NAME = "secure_prefs"

    private var cachedDeviceId: String? = null

    suspend fun getOrCreateDeviceId(context: Context) {

        if (cachedDeviceId != null) return

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
        cachedDeviceId = securePrefs.getString(DEVICE_ID_KEY, null)
        // If id not found, create a random new one
        ?: UUID.randomUUID().toString()
        // Storing new id in secure prefs
        .also { newId ->
            // Adding device id to secure prefs
            securePrefs.edit().putString(DEVICE_ID_KEY, newId).apply()
            // Caching device ID
            cachedDeviceId = newId
            // Sending Id to remote server
            repository.submitDeviceId(newId)
        }

    }

    fun getCachedDeviceId(): String? {
        return cachedDeviceId
    }

    fun setCachedDeviceId(deviceId: String?) {
        cachedDeviceId = deviceId
    }
}