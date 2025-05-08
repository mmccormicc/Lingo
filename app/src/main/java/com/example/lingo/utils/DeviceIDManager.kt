package com.example.lingo.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.lingo.data.QuizScore
import com.example.lingo.network.QuizRepository
import com.example.lingo.network.RetrofitProvider
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.UUID

object DeviceIdManager {

    val repository = QuizRepository(RetrofitProvider.quizApiService)

    private const val DEVICE_ID_KEY = "device_id"
    private const val PREF_FILE_NAME = "secure_prefs"

    private var cachedDeviceId: String? = null

    suspend fun getOrCreateDeviceId(context: Context) {

        if (cachedDeviceId != null) return

        Log.d("DeviceId", "Making a device id")

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
            // Sending Id to remote server
            submitDeviceId(newId)
        }

        Log.d("DeviceIDManager", "Current ID: $cachedDeviceId")

        // If device id was already created
        if (cachedDeviceId != null){
            // Testing submitting device id
            submitDeviceId("Fake ID 2")
            //submitDeviceId(cachedDeviceId!!)
        }
    }

    suspend fun submitDeviceId(deviceId: String) {
        try {
            // Call repository to submit the deviceId
            val response = repository.submitDeviceId(deviceId)
            if (response.isSuccessful) {
                Log.d("DeviceId", "Device ID submitted successfully")
            } else {
                Log.e("DeviceId", "Failed to submit device ID: ${response.code()}")
            }
            // Catching HttpExceptions and printing error messages
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Log.e("DeviceIDManager", "Bad Request: ${e.message()}")
                401 -> Log.e("DeviceIDManager", "Unauthorized")
                404 -> Log.e("DeviceIDManager", "Not Found")
                500 -> Log.e("DeviceIDManagerl", "Server Error")
                else -> Log.e("DeviceIDManager", "HTTP error ${e.code()}: ${e.message()}")
            }
        } catch (e: IOException) {
            Log.e("DeviceIDManager", "Network error", e)
        } catch (e: Exception) {
            Log.e("DeviceIDManager", "Unexpected error", e)
        }
    }

    fun getCachedDeviceId(): String? {
        return cachedDeviceId
    }
}