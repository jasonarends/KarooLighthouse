package com.jasonarends.karoolighthouse

import android.content.Context
import android.util.Base64
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import android.os.Handler
//import io.hammerhead.sdk.BuildConfig
import io.hammerhead.sdk.v0.SdkContext
import org.json.JSONArray
import timber.log.Timber
import android.app.PendingIntent
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import java.util.Properties


class MessageHelper(private val context: SdkContext) {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private val properties = Properties().apply {
        val inputStream = context.assets.open("config.properties")
        load(inputStream)
    }
    private fun generateHMAC(data: String): String {
        val secret  = properties.getProperty("SECRET")
        val algorithm = "HmacSHA256"
        val secretKeySpec = SecretKeySpec(secret.toByteArray(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(secretKeySpec)
        val hmacData = mac.doFinal(data.toByteArray())
        return Base64.encodeToString(hmacData, Base64.DEFAULT)
    }

    private fun validatePhoneNumber(number: String): Boolean {
        // remove non-digit characters
        val digits = number.filter {it.isDigit()}

        // check if it is 10 characters
        if (digits.length != 10) {
            return false
        }

        // check if phone number starts with a valid area code (not 1 or 0)
        if (digits[0] == '0' || digits[0] == '1') {
            return false
        }

        // check if valid central office code (not 1 or 0 in 4th place (xxx)1xx-xxxx)
        if (digits[3] == '0' || digits[3] == '1') {
            return false
        }

        return true
    }

    fun sendSms() {
        val editTextPhone1 = context.keyValueStore.getString("Phone1") ?: ""
        val editTextPhone2 = context.keyValueStore.getString("Phone2") ?: ""
        val editTextPhone3 = context.keyValueStore.getString("Phone3") ?: ""
        val editTextName = context.keyValueStore.getString("Name") ?: ""
        val editTextMessage = context.keyValueStore.getString("Message")
            ?: "https://dashboard.hammerhead.io/live/UniqueText"
        Timber.i("Loaded: $editTextPhone1, $editTextPhone2, $editTextPhone3, $editTextName, $editTextMessage")
        val name = editTextName.trim()
        val url = editTextMessage.trim()
        val phone1 = editTextPhone1.trim().filter { it.isDigit() }
        val phone2 = editTextPhone2.trim().filter { it.isDigit() }
        val phone3 = editTextPhone3.trim().filter { it.isDigit() }

        if (name.isBlank()) {
            Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(
                    context.applicationContext,
                    "Name cannot be empty",
                    Toast.LENGTH_LONG
                ).show()
            }
            Timber.i("Name is ${context.keyValueStore.getString("Name")}")
            return
        }

        if (url.isBlank() || url == "https://dashboard.hammerhead.io/live/UniqueText") {
            Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(
                    context.applicationContext,
                    "Invalid Tracking URL",
                    Toast.LENGTH_LONG
                ).show()
            }
            return
        }

        if (phone1.isBlank() && phone2.isBlank() && phone3.isBlank()) {
            Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(
                    context.applicationContext,
                    "At least one phone number must be entered",
                    Toast.LENGTH_LONG
                ).show()
            }
            return
        }

        val message = "$name has started a bike ride - track their progress here: $url"
        val toNumbers = listOf(phone1, phone2, phone3)

        val subscriptionManager = context.applicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        val smsManager = context.getSystemService(SmsManager::class.java)
        val sentIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent("SMS_SENT"),
            PendingIntent.FLAG_IMMUTABLE
        )
        for (number in toNumbers) {
            smsManager.sendTextMessage(number, null, message, sentIntent, null)
        }
        Handler(context.applicationContext.mainLooper).post {
            Toast.makeText(
                context.applicationContext,
                "SMS sent!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun sendMessage() {
        //val sdkContext = SdkContext.buildSdkContext(context)
        //sharedPreferences = context.applicationContext.getSharedPreferences("RideNotifierPrefs", Context.MODE_PRIVATE)
        // load saved data
        val editTextPhone1 = context.keyValueStore.getString("Phone1")  ?: ""
        val editTextPhone2 = context.keyValueStore.getString("Phone2") ?: ""
        val editTextPhone3 = context.keyValueStore.getString("Phone3") ?: ""
        val editTextName = context.keyValueStore.getString("Name") ?: ""
        val editTextMessage = context.keyValueStore.getString("Message") ?: "https://dashboard.hammerhead.io/live/UniqueText"
        Timber.i("Loaded: $editTextPhone1, $editTextPhone2, $editTextPhone3, $editTextName, $editTextMessage")
        val name = editTextName.trim()
        val url = editTextMessage.trim()
        val phone1 = editTextPhone1.trim().filter {it.isDigit()}
        val phone2 = editTextPhone2.trim().filter {it.isDigit()}
        val phone3 = editTextPhone3.trim().filter {it.isDigit()}

        val properties = Properties().apply {
            val inputStream = context.assets.open("config.properties")
            load(inputStream)
        }

        val baseUrl = properties.getProperty("BASE_URL")


        if (name.isBlank()) {
           Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(context.applicationContext, "Name cannot be empty", Toast.LENGTH_LONG).show()
            }
            Timber.i("Name is ${context.keyValueStore.getString("Name")}")
            return
        }

        if (url.isBlank() || url == "https://dashboard.hammerhead.io/live/UniqueText") {
            Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(context.applicationContext, "Invalid Tracking URL", Toast.LENGTH_LONG).show()
            }
            return
        }

        if (phone1.isBlank() && phone2.isBlank() && phone3.isBlank()) {
            Handler(context.applicationContext.mainLooper).post {
                Toast.makeText(
                    context.applicationContext,
                    "At least one phone number must be entered",
                    Toast.LENGTH_LONG
                ).show()
            }
            return
        }

        val message = "$name has started a bike ride - track their progress here: $url"
        val toNumbers = listOf(phone1, phone2, phone3)
            .filter { validatePhoneNumber(it) }
        val data = message + toNumbers.joinToString("")
        Timber.i("signature data: '$data'")
        val signature = generateHMAC(data)

        val payload = JSONObject()
        payload.put("message", message)
        payload.put("to_numbers", JSONArray(toNumbers))
        payload.put("signature", signature)

        CoroutineScope(Dispatchers.IO).launch {
            val response = NetworkHelper.post(baseUrl, payload.toString())
            withContext(Dispatchers.Main) {
                if (response == null) {
                    Handler(context.applicationContext.mainLooper).post {
                        Toast.makeText(
                            context.applicationContext,
                            "Failed to send SMS: Network Error",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } else if (!response.isSuccessful){
                    Handler(context.applicationContext.mainLooper).post {
                        Toast.makeText(
                            context.applicationContext,
                            "Error sending SMS: ${response.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Handler(context.applicationContext.mainLooper).post {
                        Toast.makeText(
                            context.applicationContext,
                            "SMS sent!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}