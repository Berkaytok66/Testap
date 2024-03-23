package com.berkaytok66.testapp.SuccsesActivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.zxing.integration.android.IntentIntegrator
import io.ably.lib.realtime.*
import io.ably.lib.rest.Auth
import io.ably.lib.types.*
import java.io.PrintWriter
import java.net.Socket

class SuccsesActivity : AppCompatActivity() {
    private var socket: Socket? = null
    lateinit var raporBNutton : Button
    private companion object{
        private const val TAG = "BANNER_AD_TAG"
    }
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succses)
        listSuccsesRappor()
        raporBNutton = findViewById(R.id.raporBNutton)


        MobileAds.initialize(this){
            Log.d(TAG, "onInitializationCompleted: ")
        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("PLACE_TEST_DEVICE_ID_1_HERE","PLACE_TEST_DEVICE_ID_2_HERE")).build()
        )
        adView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
        adView?.adListener = object : AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG, "onAdClicked: ")
            }

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG,"onAdCosed: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.e(TAG,"onAdFailedToLoad: ${adError.message}")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG, "onAdImpression: ")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG, "onAdLoaded: ")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG,"onAdOpened: ")
            }

            override fun onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked()
                Log.d(TAG, "onAdSwipeGestureClicked: ")
            }
        }


        raporBNutton.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.captureActivity = CustomScannerActivity::class.java
            integrator.initiateScan()
        }
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    private fun listSuccsesRappor(){
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val wifiDurumuMetni = if (SharedPreferencesManager.wifiStatus) "✓" else "x"
        val sesArtiDrumMetni = if (SharedPreferencesManager.VolumeUp) "✓" else "x"
        val sesEksiDrumMetni = if (SharedPreferencesManager.VolumeUp) "✓" else "x"
        val BluetoothDurum = if (SharedPreferencesManager.BluetoothState) "✓" else "x"
        val SimStatus = if (SharedPreferencesManager.SimStatus) "Sim Slot Algılandı" else "Sim Slot Algılanamadı"
        val SimSlotTwo = if (SharedPreferencesManager.SimSlotTwo) "Çift Sim Algılandı" else "Cift Sim Algılanamadı"
        val HoparlorStatus = if (SharedPreferencesManager.HoparlorStatus) "✓" else "x"
        val AhizeStatus = if (SharedPreferencesManager.AhizeStatus) "✓" else "x"
        val MicStatus = if (SharedPreferencesManager.MicStatus) "✓" else "x"
        val VibrationState = if (SharedPreferencesManager.VibrationState) "✓" else "x"
        val ScreenState = if (SharedPreferencesManager.ScreenState) "✓" else "x"
        val PixelColorRed = if (SharedPreferencesManager.PixelColorRed) "x" else "✓"
        val PixelColorGreen = if (SharedPreferencesManager.PixelColorGreen) "x" else "✓"
        val PixelColorBlue = if (SharedPreferencesManager.PixelColorBlue) "x" else "✓"
        val PixelColorYellow = if (SharedPreferencesManager.PixelColorYellow) "x" else "✓"
        val FlashState = if (SharedPreferencesManager.FlashState) "✓" else "x"
        val ScrenBrightness = if (SharedPreferencesManager.ScrenBrightness) "✓" else "x"
        val Sensor = if (SharedPreferencesManager.Sensor) "✓" else "x"
        val SensorY = if (SharedPreferencesManager.SensorY) "✓" else "x"
        val SensorU = if (SharedPreferencesManager.SensorU) "✓" else "x"
        val BackCamera = if (SharedPreferencesManager.BackCamera) "✓" else "x"
        val frontCamera = if (SharedPreferencesManager.frontCamera) "✓" else "x"


        // Örnek veri listesi
        val dataList = listOf(
            Pair("Tuş Testi Sonuçları", "başlık"),
            Pair("Ses ( + ) Testi Sonuçları", sesArtiDrumMetni),
            Pair("Ses ( - ) Testi Sonuçları", sesEksiDrumMetni),
            Pair("Wifi Testi Sonuçları", "başlık"),
            Pair("Wifi Durumu", wifiDurumuMetni),
            Pair("Bulunan Wifi Sayısı", SharedPreferencesManager.NumberOfWifiFound.toString()),
            Pair("Bluetooth Testi Sonuçları", "başlık"),
            Pair("Bluetooth Durumu", BluetoothDurum),
            Pair("Sim Slot Sonuçları", "başlık"),
            Pair("Sim Slot Durumu", SimStatus),
            Pair("Sim Slot Ayrıntılar", SharedPreferencesManager.SimDetalis),
            Pair("Cift Sim Slot", SimSlotTwo),
            Pair("Batarya Durumu", "başlık"),
            Pair("Batarya Yüzdesi", SharedPreferencesManager.BatteryPercentage),
            Pair("Batarya Sağlık", SharedPreferencesManager.BatteryHealth),
            Pair("Batarya Durumu", SharedPreferencesManager.BatteryStatus),
            Pair("Batarya Takılımı", SharedPreferencesManager.BatteryPlugged),
            Pair("Batarya Voltaj", SharedPreferencesManager.BatteryVoltage),
            Pair("Batarya Sıcaklık", SharedPreferencesManager.BatteryTemperature),
            Pair("Hoperlör Ve Ahize", "başlık"),
            Pair("Hoperlör Durumu", HoparlorStatus),
            Pair("Ahize Durumu", AhizeStatus),
            Pair("Mikrafon", "başlık"),
            Pair("Mikrafon Durumu", MicStatus),
            Pair("Titreşim Test","başlık"),
            Pair("Titreşim Motoru", VibrationState),
            Pair("Ekran Test","başlık"),
            Pair("Ekran Durumu", ScreenState),
            Pair("Hatalı Bölüm Numarası", SharedPreferencesManager.ScreenError),
            Pair("Pixel Test","başlık"),
            Pair("Kırmızı Renk", PixelColorRed),
            Pair("Yeşil Renk", PixelColorGreen),
            Pair("Mavi Renk", PixelColorBlue),
            Pair("Sarı Renk", PixelColorYellow),
            Pair("Flaş Test","başlık"),
            Pair("Flas Durumu", FlashState),
            Pair("Flas Hata Varmı", SharedPreferencesManager.FlashStateExplanation),
            Pair("Ekran Parlaklık testi","başlık"),
            Pair("Ekran Parlaklık", ScrenBrightness),
            Pair("Yakınlık Sensörü","başlık"),

            Pair("Sensör Durumu",Sensor),
            Pair("Yakınlık Sensörü",SensorY),
            Pair("Uzaklık Sensörü",SensorU),
            Pair("Kamera","başlık"),
            Pair("Arka Kamera", BackCamera),
            Pair("Ön Kamera", frontCamera),
        )


        for ((first, second) in dataList) {
            val tableRow = TableRow(this).apply {
                layoutParams =
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, WRAP_CONTENT)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            if (second == "başlık") {
                // Başlık için tek TextView
                val textView = TextView(this).apply {
                    text = first
                    setPadding(16, 8, 16, 8)
                    setBackgroundResource(R.drawable.cell_border)
                    gravity = Gravity.CENTER
                    layoutParams = TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f) // Başlık için font boyutunu büyüt
                    setTextColor(Color.BLUE)
                }

                tableRow.addView(
                    textView,
                    TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, WRAP_CONTENT, 1f)
                )
            } else {
                // Normal satır için iki TextView
                val textView1 = TextView(this).apply {
                    text = first
                    setPadding(16, 8, 16, 8)
                    setBackgroundResource(R.drawable.cell_border)
                    layoutParams = TableRow.LayoutParams(0, WRAP_CONTENT, 1f)
                }

                val textView2 = TextView(this).apply {
                    text = second.toString()
                    setPadding(16, 8, 16, 8)
                    setBackgroundResource(R.drawable.cell_border)
                    layoutParams = TableRow.LayoutParams(0, WRAP_CONTENT, 1f)
                    gravity = Gravity.CENTER
                }

                tableRow.addView(textView1)
                tableRow.addView(textView2)
            }

            tableLayout.addView(tableRow)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.d("qrrr", "QR Kod okuma iptal edildi")
            } else {
                Log.d("qrrr", "QR Kod: ${result.contents}")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Eğer mevcut aktiviteyi kapatmak isterseniz, aşağıdaki satırı ekleyin
        finish()
    }


}