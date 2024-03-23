package com.berkaytok66.testapp.Activity.Battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.ListView
import com.berkaytok66.testapp.Activity.SoundActivity.SoundActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class BatteryActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val batteryInfoList = ArrayList<String>()

    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)
        listView = findViewById(R.id.listViewBatteryInfo)
        registerBatteryInfoReceiver()
    }

    private fun registerBatteryInfoReceiver() {
        val batteryInfoReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPct = level * 100 / scale.toFloat()
                val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0)
                val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
                val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
                val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0

                batteryInfoList.clear() // Liste temizle
                batteryInfoList.add("Battery Percentage: $batteryPct%")
                batteryInfoList.add("Health: ${getBatteryHealth(health)}")
                batteryInfoList.add("Status: ${getBatteryStatus(status)}")
                batteryInfoList.add("Plugged: ${isPlugged(plugged)}")
                batteryInfoList.add("Voltage: $voltage V")
                batteryInfoList.add("Temperature: $temperature°C")

                SharedPreferencesManager.BatteryPercentage = batteryPct.toString()
                SharedPreferencesManager.BatteryHealth = getBatteryHealth(health)
                SharedPreferencesManager.BatteryStatus = getBatteryStatus(status)
                SharedPreferencesManager.BatteryPlugged = isPlugged(plugged)
                SharedPreferencesManager.BatteryVoltage = "$voltage V"
                SharedPreferencesManager.BatteryTemperature = "$temperature°C"

                updateListView()
            }
        }
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryInfoReceiver, intentFilter)
        runnable = Runnable {
            if (GenelTutucu.ActivityNext){
                StartActivity.launchActivity(this,MainActivity::class.java)
                finish()
            }else{
                StartActivity.launchActivity(this,SoundActivity::class.java )
                finish()
            }
        }
        handler.postDelayed(runnable,7000)

    }

    private fun updateListView() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, batteryInfoList)
        listView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
    private fun getBatteryHealth(health: Int): String {
        return when (health) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
            else -> "Unknown"
        }
    }

    private fun getBatteryStatus(status: Int): String {
        return when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
            BatteryManager.BATTERY_STATUS_FULL -> "Full"
            else -> "Unknown"
        }
    }

    private fun isPlugged(plugged: Int): String {
        return when (plugged) {
            BatteryManager.BATTERY_PLUGGED_AC -> "AC"
            BatteryManager.BATTERY_PLUGGED_USB -> "USB"
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
            else -> "Not Plugged"
        }
    }
}