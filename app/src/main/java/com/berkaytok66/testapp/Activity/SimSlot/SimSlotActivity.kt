package com.berkaytok66.testapp.Activity.SimSlot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.berkaytok66.testapp.Activity.Battery.BatteryActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class SimSlotActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim_slot)
        listView = findViewById(R.id.listView4)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
        } else {
            displaySimInformation()
        }
        checkSimCardStatus()
    }
    @SuppressLint("MissingPermission")
    private fun displaySimInformation() {
        val simInfoList = ArrayList<String>()
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val subscriptionManager = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            val subscriptionInfoList: List<SubscriptionInfo> = subscriptionManager.activeSubscriptionInfoList
            for (subscriptionInfo in subscriptionInfoList) {
                val carrierName = subscriptionInfo.carrierName.toString()
                val simSlotIndex = subscriptionInfo.simSlotIndex
                SharedPreferencesManager.SimDetalis = carrierName
                simInfoList.add("Slot $simSlotIndex: $carrierName")
            }
      //      GenelTutucu.SimSlotTwo = true
            SharedPreferencesManager.SimSlotTwo = true
            SharedPreferencesManager.SimStatus = true


            StartActivityy()
        } else {
            // For older versions, you can only get basic SIM information.
            val simState = telephonyManager.simState
            val isSimReady = simState == TelephonyManager.SIM_STATE_READY
            val operatorName = if (isSimReady) telephonyManager.networkOperatorName else "N/A"
            simInfoList.add("SIM: $operatorName")
            SharedPreferencesManager.SimStatus = true
            StartActivityy()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, simInfoList)
        listView.adapter = adapter
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            displaySimInformation()
        }
    }

    private fun checkSimCardStatus() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simState = telephonyManager.simState

        when (simState) {
            TelephonyManager.SIM_STATE_ABSENT -> logSimStatus("SIM kart yok")
            TelephonyManager.SIM_STATE_NETWORK_LOCKED -> logSimStatus("SIM network tarafından kilitli")
            TelephonyManager.SIM_STATE_PIN_REQUIRED -> logSimStatus("SIM PIN gerektiriyor")
            TelephonyManager.SIM_STATE_PUK_REQUIRED -> logSimStatus("SIM PUK gerektiriyor")
            TelephonyManager.SIM_STATE_READY -> logSimStatus("SIM hazır")
            TelephonyManager.SIM_STATE_UNKNOWN -> logSimStatus("SIM durumu bilinmiyor")
            else -> logSimStatus("Tanımlanamayan SIM durumu")
        }
    }

    fun logSimStatus(message: String) {

        SharedPreferencesManager.SimStatus = false
        SharedPreferencesManager.SimDetalis = message
      // GenelTutucu.SimStatus = false
      // GenelTutucu.SimDetalis = message
        StartActivityy()
    }
   fun StartActivityy(){
       runnable = Runnable {
           if (GenelTutucu.ActivityNext){
               StartActivity.launchActivity(this,MainActivity::class.java)
           }else{
               StartActivity.launchActivity(this,BatteryActivity::class.java)
           }
       }
       handler.postDelayed(runnable,5000)
   }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}