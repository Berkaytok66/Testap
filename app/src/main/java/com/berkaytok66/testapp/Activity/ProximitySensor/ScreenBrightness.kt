package com.berkaytok66.testapp.Activity.ProximitySensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.berkaytok66.testapp.Activity.CameraActivity.CameraActivity

import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class ScreenBrightness : AppCompatActivity() {
    private lateinit var flashSwitch: Switch
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null

    lateinit var negativeBNutton: Button
    lateinit var pozitiveBNutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_brightness)

        flashSwitch = findViewById(R.id.flashSwitch)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        negativeBNutton = findViewById(R.id.negativeBNutton)
        pozitiveBNutton = findViewById(R.id.pozitiveBNutton)

        negativeBNutton.setOnClickListener {
            SharedPreferencesManager.FlashState = false
            goToActivity()
        }
        pozitiveBNutton.setOnClickListener {
            SharedPreferencesManager.FlashState = true
            goToActivity()
        }

        try {
            // Kamera ID'sini alın
            for (id in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraId = id
                    break
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (cameraId != null) {
                try {
                    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                        cameraManager.setTorchMode(cameraId!!, isChecked)

                    } else {
                        SharedPreferencesManager.FlashState = false
                        SharedPreferencesManager.FlashStateExplanation = "Cihazda flash ışığı yok!"
                        goToActivity()
                    }
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            } else {
                SharedPreferencesManager.FlashState = false
                SharedPreferencesManager.FlashStateExplanation = "Flash ışığı kullanılamaz!"
                goToActivity()
            }
        }
    }

    fun goToActivity (){
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this,MainActivity::class.java)
        }else{
            StartActivity.launchActivity(this,BrightnessActivity::class.java)
        }
    }
}
