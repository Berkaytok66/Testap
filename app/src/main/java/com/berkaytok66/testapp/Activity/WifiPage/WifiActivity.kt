package com.berkaytok66.testapp.Activity.WifiPage

import WifiAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkaytok66.testapp.Activity.Bluetooth.BluetoothActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class WifiActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var wifiManager: WifiManager
    private var wifiAdapter: WifiAdapter? = null
    private val PERMISSIONS_REQUEST_CODE = 100
    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                updateWifiList()
            } else {
                // handle failure
                Log.d("WifiActivity", "Wi-Fi scan failed")

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        recyclerView = findViewById(R.id.wifiRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_CODE)
        } else {
            startWifiScan()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateWifiList() {
        val wifiList = wifiManager.scanResults
        wifiAdapter = WifiAdapter(wifiList)
        recyclerView.adapter = wifiAdapter

        // Wi-Fi ağları sayısı ve sinyal gücünü consola yazdır
        Log.d("WifiActivity", "Number of Wi-Fi networks found: ${wifiList.size}")
        if (wifiList.size == 0) {

            Log.d("WifiActivity", " Wifi Bulunamadı")
            SharedPreferencesManager.wifiStatus = false
            SharedPreferencesManager.NumberOfWifiFound = 0
           // GenelTutucu.wifiStatus = false
           // GenelTutucu.NumberOfWifiFound = 0
            if (GenelTutucu.ActivityNext) {
                StartActivity.launchActivity(this,MainActivity::class.java)
                GenelTutucu.ActivityNext = false
            }else{
                StartActivity.launchActivity(this,BluetoothActivity::class.java)
                GenelTutucu.ActivityNext = false
            }
        }else{
            for (scanResult in wifiList) {
                val ssid = scanResult.SSID
                val signalStrength = WifiManager.calculateSignalLevel(scanResult.level, 5)
                Log.d("WifiActivity", "SSID: $ssid, Signal Strength: $signalStrength/5")
            }
            SharedPreferencesManager.wifiStatus = true
            SharedPreferencesManager.NumberOfWifiFound = wifiList.size
      //      GenelTutucu.NumberOfWifiFound =wifiList.size
            runnable = Runnable {
                if (GenelTutucu.ActivityNext){
                    StartActivity.launchActivity(this,MainActivity::class.java)

                }else{
                    StartActivity.launchActivity(this,BluetoothActivity::class.java)

                }
            }
            handler.postDelayed(runnable, 6000)

        }

    }

    private fun startWifiScan() {
        registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startWifiScan()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        unregisterReceiver(wifiScanReceiver)
    }
}
