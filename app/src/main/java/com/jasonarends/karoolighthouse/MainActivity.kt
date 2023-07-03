package com.jasonarends.karoolighthouse

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.hammerhead.sdk.v0.SdkContext

class MainActivity : ComponentActivity() {
    private val kvStore by lazy {
        SdkContext.buildSdkContext(this).keyValueStore
    }
    //private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sharedPreferences = getSharedPreferences("RideNotifierPrefs", Context.MODE_PRIVATE)

        val editTextPhone1 = findViewById<EditText>(R.id.editTextPhone1)
        val editTextPhone2 = findViewById<EditText>(R.id.editTextPhone2)
        val editTextPhone3 = findViewById<EditText>(R.id.editTextPhone3)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // load saved data
        //editTextPhone1.setText(sharedPreferences.getString("Phone1", ""))
        //editTextPhone2.setText(sharedPreferences.getString("Phone2", ""))
        //editTextPhone3.setText(sharedPreferences.getString("Phone3", ""))
        //editTextName.setText(sharedPreferences.getString("Name", ""))
        //editTextMessage.setText(sharedPreferences.getString("Message", "https://dashboard.hammerhead.io/live/UniqueText"))

        kvStore.getString("Phone1")?.let { editTextPhone1.setText(it) }
        kvStore.getString("Phone2")?.let { editTextPhone2.setText(it) }
        kvStore.getString("Phone3")?.let { editTextPhone3.setText(it) }
        kvStore.getString("Name")?.let { editTextName.setText(it) }
        val message = kvStore.getString("Message")
        if (message != null) {
            editTextMessage.setText(message)
        } else {
            editTextMessage.setText("https://dashboard.hammerhead.io/live/UniqueText")
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 42)
        }

        buttonSave.setOnClickListener {
            //save data
            //val editor = sharedPreferences.edit()
            kvStore.putString("Phone1", editTextPhone1.text.toString())
            kvStore.putString("Phone2", editTextPhone2.text.toString())
            kvStore.putString("Phone3", editTextPhone3.text.toString())
            kvStore.putString("Name", editTextName.text.toString())
            kvStore.putString("Message", editTextMessage.text.toString())
            //editor.apply()
        }

    }


}
