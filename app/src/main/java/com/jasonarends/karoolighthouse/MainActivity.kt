package com.jasonarends.karoolighthouse

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.hammerhead.sdk.v0.SdkContext

class MainActivity : ComponentActivity() {
    private val kvStore by lazy {
        SdkContext.buildSdkContext(this).keyValueStore
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPhone1 = findViewById<EditText>(R.id.editTextPhone1)
        val editTextPhone2 = findViewById<EditText>(R.id.editTextPhone2)
        val editTextPhone3 = findViewById<EditText>(R.id.editTextPhone3)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextMessage = findViewById<EditText>(R.id.editTextMessage)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val switchSmsGateway = findViewById<Switch>(R.id.switchSmsGateway)
        val sampleUrl = getString(R.string.sample_hammerhead_url)

        kvStore.getString("Phone1")?.let { editTextPhone1.setText(it) }
        kvStore.getString("Phone2")?.let { editTextPhone2.setText(it) }
        kvStore.getString("Phone3")?.let { editTextPhone3.setText(it) }
        kvStore.getString("Name")?.let { editTextName.setText(it) }
        val message = kvStore.getString("Message")
        if (message != null) {
            editTextMessage.setText(message)
        } else {
            editTextMessage.setText(sampleUrl)
        }
        val isSmsGatewayUsed = kvStore.getDouble("isSmsGatewayUsed") ?: 0.0
        switchSmsGateway.isChecked = isSmsGatewayUsed == 1.0


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 42)
        }

        buttonSave.setOnClickListener {
            kvStore.putString("Phone1", editTextPhone1.text.toString())
            kvStore.putString("Phone2", editTextPhone2.text.toString())
            kvStore.putString("Phone3", editTextPhone3.text.toString())
            kvStore.putString("Name", editTextName.text.toString())
            kvStore.putString("Message", editTextMessage.text.toString())
        }

        switchSmsGateway.setOnCheckedChangeListener { _, isChecked ->
            kvStore.putDouble("isSmsGatewayUsed", if (isChecked) 1.0 else 0.0)
        }


    }


}
