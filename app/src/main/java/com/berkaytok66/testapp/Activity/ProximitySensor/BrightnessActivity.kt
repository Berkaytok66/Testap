package com.berkaytok66.testapp.Activity.ProximitySensor

import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import com.berkaytok66.testapp.Activity.CameraActivity.CameraActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class BrightnessActivity : AppCompatActivity() {
    lateinit var negativeBNutton: Button
    lateinit var pozitiveBNutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brightness)
        pozitiveBNutton = findViewById(R.id.pozitiveBNutton)
        negativeBNutton = findViewById(R.id.negativeBNutton)
        val brightnessSeekBar = findViewById<SeekBar>(R.id.brightnessSeekBar)

        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setScreenBrightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Bu fonksiyon boş bırakılabilir.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Bu fonksiyon boş bırakılabilir.
            }
        })
        negativeBNutton.setOnClickListener {
            SharedPreferencesManager.ScrenBrightness = false
            goActivity()
        }
        pozitiveBNutton.setOnClickListener {
            SharedPreferencesManager.ScrenBrightness = true
            goActivity()
        }
    }
    private fun setScreenBrightness(brightnessValue: Int) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightnessValue / 100.0f // Değeri 0.0 - 1.0 arasına dönüştür
        window.attributes = layoutParams

        // Sistem ayarlarında parlaklığı değiştirmek için aşağıdaki yorum satırını kaldırın.
        // Ancak bu, WRITE_SETTINGS izni gerektirir.
        // Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue)
    }
    fun goActivity(){
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this, MainActivity::class.java)
        }else{
            StartActivity.launchActivity(this, CameraActivity::class.java)
        }
    }
}