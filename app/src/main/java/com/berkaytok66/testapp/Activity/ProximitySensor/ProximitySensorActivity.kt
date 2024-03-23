package com.berkaytok66.testapp.Activity.ProximitySensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class ProximitySensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var messageTextView: TextView
    private lateinit var pozitiveButton: Button
    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity_sensor)

        messageTextView = findViewById(R.id.messageTextView)
        pozitiveButton = findViewById(R.id.pozitiveBNutton)

        // SensorManager'ı başlat
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        pozitiveButton.setOnClickListener {
            SharedPreferencesManager.Sensor = false
            goActivity()
        }
        if (proximitySensor == null) {
            SharedPreferencesManager.Sensor = false
            Toast.makeText(this, "Yakınlık sensörü bulunamadı!", Toast.LENGTH_SHORT).show()
           // finish() // Eğer sensör yoksa aktiviteyi kapat
            goActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.also { proximity ->
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            // Yakınlık değerini al
            val distance = event.values[0]
            if (distance < proximitySensor!!.maximumRange) {
                // Nesne sensöre yakın
                SharedPreferencesManager.Sensor = true
                SharedPreferencesManager.SensorY = true
                goActivity()

            } else {
                // Nesne sensörden uzak
                SharedPreferencesManager.Sensor = true
                SharedPreferencesManager.SensorU = true

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Bu örnekte doğruluk değişiklikleriyle ilgili bir işlem yapmıyoruz
    }
    fun goActivity(){
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this, MainActivity::class.java)
        }else{
            //oto test Gidelecek acativity

            StartActivity.launchActivity(this, ScreenBrightness::class.java)
        }
    }
}
