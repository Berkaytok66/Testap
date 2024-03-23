package com.berkaytok66.testapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.berkaytok66.testapp.Fragment.ManuelFragment
import com.berkaytok66.testapp.Fragment.OtoFragment
import com.berkaytok66.testapp.Fragment.RaporFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.berkaytok66.testapp.Class.SharedPreferencesManager


class  MainActivity : AppCompatActivity() {


    companion object {
        const val PERMISSIONS_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesManager.init(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkAndRequestPermissions()
        }


        val bottomNavigation =findViewById<CurvedBottomNavigation>(R.id.bottomNavigation);
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1,"Oto Test",R.drawable.oto_icons
            )
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2,"Manuel Test",R.drawable.manuel)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3,"Rapor",R.drawable.report_icon)
        )
        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> {
                    replaceFragment(OtoFragment())
                }
                2 -> {
                    replaceFragment(ManuelFragment())
                }
                3 -> {
                    replaceFragment(RaporFragment())
                }
            }
        }
        // defaultta acılacak fragment
        replaceFragment(ManuelFragment())
        bottomNavigation.show(2)

    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer,fragment)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkAndRequestPermissions() {
        val permissionsNeeded = arrayListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.VIBRATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WAKE_LOCK
        )

        val permissionsToRequest = permissionsNeeded.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for ((index, permission) in permissions.withIndex()) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            // Kullanıcı bu izni reddetti, gerekiyorsa işlem yap
                        }
                    }
                }
                return
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        AlertDialog.Builder(this)
            .setTitle("Uygulamayı Kapat")
            .setMessage("Testap'ı kapatmak istediğinizden emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                finishAffinity() // Uygulamanın tüm aktivitelerini kapatır
            }
            .setNegativeButton("Hayır", null)
            .show()
    }
}