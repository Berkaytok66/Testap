package com.berkaytok66.testapp.Activity.MicTestActivity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.berkaytok66.testapp.Activity.VibrationActivity.VibrationActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MicTestActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private val entries = ArrayList<Entry>()
    private var microphoneListener: MicrophoneListener? = null
    private val RECORD_AUDIO_PERMISSION_CODE = 1
    private var maxLevelReached = false
    private val threshold = 1000 // dB eşik değeri
    private val checkInterval = 30000 // 30 saniye
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mic_test)
        SharedPreferencesManager.init(this)
        lineChart = findViewById(R.id.lineChart)
        setupChart()

        microphoneListener = MicrophoneListener { level ->
            runOnUiThread {
                addEntry(level)
            }
        }

        checkPermissions()
    }

    private fun setupChart() {
        lineChart.description.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.axisMinimum = 0f // start at zero
        lineChart.xAxis.axisMinimum = 0f // start at zero
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(false)
    }

    private fun addEntry(level: Float) {
        entries.add(Entry(entries.size.toFloat(), level))
        val dataSet = LineDataSet(entries, "Ses Seviyesi").apply {
            val color = if (level > threshold) Color.RED else Color.GREEN
            setColor(color)
            setLineWidth(if (level > threshold) 4f else 2f)
            setDrawCircles(false)
            setDrawValues(false)
        }

        lineChart.data = LineData(dataSet)
        lineChart.notifyDataSetChanged()
        lineChart.invalidate()

        if (level > threshold) {
            maxLevelReached = true
            SharedPreferencesManager.MicStatus = true
            launchVibrationActivity()
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_CODE)
        } else {
            startMicrophoneTest()
        }
    }

    private fun startMicrophoneTest() {
        microphoneListener?.startListening()
        maxLevelReached = false

        handler.postDelayed({
            if (!maxLevelReached) {
                runOnUiThread {
                    Toast.makeText(this, "Ses seviyesi son 30 saniye içinde eşik değerine ulaşmadı!", Toast.LENGTH_LONG).show()
                    SharedPreferencesManager.MicStatus = false
                    launchVibrationActivity()

                }
            }
        }, checkInterval.toLong())
    }

    private fun stopMicrophoneTest() {
        microphoneListener?.stopListening()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startMicrophoneTest()
            } else {
                Toast.makeText(this, "Mikrofon izni gereklidir", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMicrophoneTest()
        handler.removeCallbacksAndMessages(null)
    }

    private fun launchVibrationActivity() {
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this, MainActivity::class.java)
        }else{
            StartActivity.launchActivity(this, VibrationActivity::class.java)
        }

    }
}
