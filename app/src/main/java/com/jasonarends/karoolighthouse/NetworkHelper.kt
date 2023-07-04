package com.jasonarends.karoolighthouse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.IOException

object NetworkHelper {
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    private val client = OkHttpClient()

    suspend fun post(url: String, json: String): Response? = withContext(Dispatchers.IO) {
        Timber.i("Got request to post $json to $url")
        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        return@withContext try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                Timber.i("Got response $response")
                response
            }
        } catch (e: Exception){
            Timber.e(e, "Network error when making POST request")
            e.printStackTrace()
            throw e
        }
    }
}