package com.berkaytok66.testapp.Activity.Bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.os.postDelayed
import com.berkaytok66.testapp.Activity.Battery.BatteryActivity
import com.berkaytok66.testapp.Activity.SimSlot.SimSlotActivity
import com.berkaytok66.testapp.Activity.WifiPage.WifiActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.CustomAlertDialogCompanent
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class BluetoothActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var listView: ListView
    private val foundDevices = mutableListOf<String>()
    val delayMillis = 10000 // 10 saniye
    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private val bluetoothScanTimeout = 10000 // 10 saniye
    private var isDeviceFound = false
    private val receiver = object : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                val deviceName = device.name ?: "Unknown Device"
                val deviceHardwareAddress = device.address // MAC address
                foundDevices.add("$deviceName ($deviceHardwareAddress)")
                isDeviceFound = true
                updateListView()
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        listView = findViewById(R.id.bluetooth_list_view)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(this, "hata kodu 263", Toast.LENGTH_SHORT).show()
            return
        }
        startBluetoothDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)



        bluetoothAdapter?.startDiscovery()



    }
    @SuppressLint("MissingPermission")
    private fun startBluetoothDiscovery(){
        if (!bluetoothAdapter!!.isEnabled) {
            // Bluetooth'u etkinleştirmek için istek gönder
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)

        }else{
            if  (bluetoothAdapter!!.startDiscovery()){
                isDeviceFound = false
                runnable = Runnable {
                    if (!isDeviceFound) {
                        dialogAlerd("Bluetooth Kontrol","Tarama sonuçları başarısız oldu!\nOlası Nedenler:\n-Bluetooth Kapalı Olabilir\n-Çevrenizde Bluetooth Ağı olmayabilir.\nRaporlamak istermisiniz? ")
                    }
                }
                handler.postDelayed(runnable, delayMillis.toLong())

            }else{
                dialogAlerd("Bluetooth Kontrol","Bluetooth taraması başlatılamadı!\nOlası Nedenler:\n-Bluetooth Kapalı Olabilir\n-İzinler Atlanmış Olabir.\n-Donanımsan bir arıza olabilir\nCözüm Yolları:\nCihaz Atarlarından İzin ayarlarını kontrol edin\nRaporlamak istermisiniz? ")
            }
        }

        runnable = Runnable {
            if (isDeviceFound) {
                if (GenelTutucu.ActivityNext){
                   // GenelTutucu.BluetoothState = true
                    SharedPreferencesManager.BluetoothState =true
                    val BlActivite = Intent(this, MainActivity::class.java)
                    startActivity(BlActivite)
                    finish()
                }else{
                    //GenelTutucu.BluetoothState = true
                    SharedPreferencesManager.BluetoothState =true
                    val BlActivite = Intent(this, SimSlotActivity::class.java)
                    startActivity(BlActivite)
                    finish()
                }

            } else {
                dialogAlerd("Bluetooth Kontrol","Tarama sonuçları başarısız oldu!\nOlası Nedenler:\n-Bluetooth Kapalı Olabilir\n-Çevrenizde Bluetooth Ağı olmayabilir.\nRaporlamak istermisiniz? ")
            }
        }
        handler.postDelayed(runnable,10000)

    }
    private fun updateListView() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, foundDevices)
        listView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }
    fun dialogAlerd(Title: String,message:String){
        runnable  = Runnable {

        }

        handler.postDelayed({
            // 10 saniye sonra bu kod bloğu çalışır
            if (foundDevices.isNotEmpty()){

                val customDialog = CustomAlertDialogCompanent(
                    context = this,
                    title = Title,
                    message = message,
                    icon = R.mipmap.ic_launcher, // icon resource
                    positiveButtonText = "Rapor Al",
                    negativeButtonText = "Tekrar Dene",
                    positiveButtonListener = DialogInterface.OnClickListener { dialog, which ->
                        // Pozitif butona basıldığında yapılacak işlemler
                        if (GenelTutucu.ActivityNext){
                            //GenelTutucu.BluetoothState = false
                            SharedPreferencesManager.BluetoothState =false
                            val BlActivite = Intent(this, MainActivity::class.java)
                            startActivity(BlActivite)
                            finish()
                        }else{
                           // GenelTutucu.BluetoothState = false
                            SharedPreferencesManager.BluetoothState =false
                            val BlActivite = Intent(this, SimSlotActivity::class.java)
                            startActivity(BlActivite)
                            finish()
                        }
                    },
                    negativeButtonListener = DialogInterface.OnClickListener { dialog, which ->
                        // Negatif butona basıldığında yapılacak işlemler

                        dialogAlerd("Bluetooth Kontrol","Tarama sonuçları başarısız oldu!\nOlası Nedenler:\n-Bluetooth Kapalı Olabilir\n-Çevrenizde Bluetooth Ağı olmayabilir.\nRaporlamak istermisiniz? ")
                        dialog.dismiss()
                    }
                )
                customDialog.show()

            }else{
                if (GenelTutucu.ActivityNext){

                    val BlActivite = Intent(this, MainActivity::class.java)
                    startActivity(BlActivite)
                    finish()
                }else{

                    val BlActivite = Intent(this, SimSlotActivity::class.java)
                    startActivity(BlActivite)
                    finish()
                }

            }

        }, delayMillis.toLong())
    }

}