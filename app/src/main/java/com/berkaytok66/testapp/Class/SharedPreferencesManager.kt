package com.berkaytok66.testapp.Class

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPreferencesManager {
    private const val PREF_NAME = "MySharedPrefs"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    var wifiStatus: Boolean
        get() = sharedPreferences.getBoolean("wifiStatus", false) // Wifi Çalışıyormu
        set(value) = editor.putBoolean("wifiStatus", value).apply()

    var NumberOfWifiFound: Int
        get() = sharedPreferences.getInt("NumberOfWifiFound", 0)// Kaç Ağ Tesğit edildi
        set(value) = editor.putInt("NumberOfWifiFound", value).apply()

    var VolumeUp: Boolean
        get() = sharedPreferences.getBoolean("VolumeUp", false) //ses + çalışıyormu ?
        set(value) = editor.putBoolean("VolumeUp", value).apply()

    var VolumeDown: Boolean
        get() = sharedPreferences.getBoolean("VolumeDown", false) // Ses- calışıyormu ?
        set(value) = editor.putBoolean("VolumeDown", value).apply()

    var BluetoothState: Boolean
        get() = sharedPreferences.getBoolean("BluetoothState", false) // BLuethoot Durumu ?
        set(value) = editor.putBoolean("BluetoothState", value).apply()

    var SimStatus: Boolean
        get() = sharedPreferences.getBoolean("SimStatus", false) // Sim kart algılandımı ?
        set(value) = editor.putBoolean("SimStatus", value).apply()

    var SimDetalis: String?
        get() = sharedPreferences.getString("SimDetalis", "") // Algılanmadı ise durumu
        set(value) = editor.putString("SimDetalis", value).apply()

    var SimSlotTwo: Boolean
        get() = sharedPreferences.getBoolean("SimSlotTwo", false) // Cift sim ise
        set(value) = editor.putBoolean("SimSlotTwo", value).apply()

    var BatteryPercentage: String?
        get() = sharedPreferences.getString("BatteryPercentage", "") //Battery yuzdesi
        set(value) = editor.putString("BatteryPercentage", value).apply()

    var BatteryHealth: String?
        get() = sharedPreferences.getString("BatteryHealth", "") // Sağlık
        set(value) = editor.putString("BatteryHealth", value).apply()

    var BatteryStatus: String?
        get() = sharedPreferences.getString("BatteryStatus", "") // Durum
        set(value) = editor.putString("BatteryStatus", value).apply()

    var BatteryPlugged: String?
        get() = sharedPreferences.getString("BatteryPlugged", "") // Takılı
        set(value) = editor.putString("BatteryPlugged", value).apply()

    var BatteryVoltage: String?
        get() = sharedPreferences.getString("BatteryVoltage", "") // voltaj
        set(value) = editor.putString("BatteryVoltage", value).apply()

    var BatteryTemperature: String?
        get() = sharedPreferences.getString("BatteryTemperature", "") // ısı
        set(value) = editor.putString("BatteryTemperature", value).apply()

    var HoparlorStatus: Boolean
        get() = sharedPreferences.getBoolean("HoparlorStatus", false) //false ise hoperlor çalışmıyor
        set(value) = editor.putBoolean("HoparlorStatus", value).apply()

    var AhizeStatus: Boolean
        get() = sharedPreferences.getBoolean("AhizeStatus", false) // false ise ahize ses yok
        set(value) = editor.putBoolean("AhizeStatus", value).apply()

  //  var MicStatus: Boolean
  //      get() = sharedPreferences.getBoolean("MicStatus", false) // Mikrafon ke
  //      set(value) = editor.putBoolean("MicStatus", value).apply()
    var MicStatus: Boolean
        get() = sharedPreferences.getBoolean("MicStatus", false)
        set(value) {
            try {
                editor.putBoolean("MicStatus", value).apply()
            } catch (e: Exception) {
                Log.e("SharedPreferences", "Hata: ${e.message}")
            }
        }


    var VibrationState: Boolean
        get() = sharedPreferences.getBoolean("VibrationState", false) // Titreşim
        set(value) = editor.putBoolean("VibrationState", value).apply()

    var ScreenState: Boolean
        get() = sharedPreferences.getBoolean("ScreenState", false) // dokunmatik durumu
        set(value) = editor.putBoolean("ScreenState", value).apply()

    var ScreenError : String?
        get() = sharedPreferences.getString("ScreenError","") // ekran dokunmatiğinde hata varsa hata yeri
        set(value) = editor.putString("ScreenError",value).apply()


    var PixelColorRed: Boolean
        get() = sharedPreferences.getBoolean("PixelColorRed", false) // Kırmızı renk pixel tesit
        set(value) = editor.putBoolean("PixelColorRed", value).apply()
    var PixelColorGreen: Boolean
        get() = sharedPreferences.getBoolean("PixelColorGreen", false)// Yeşil renk pixel tesit
        set(value) = editor.putBoolean("PixelColorGreen", value).apply()
    var PixelColorBlue: Boolean
        get() = sharedPreferences.getBoolean("PixelColorBlue", false)// Mavi renk pixel tesit
        set(value) = editor.putBoolean("PixelColorBlue", value).apply()
    var PixelColorYellow: Boolean
        get() = sharedPreferences.getBoolean("PixelColorYellow", false)// Sarı renk pixel tesit
        set(value) = editor.putBoolean("PixelColorYellow", value).apply()

    var FlashState : Boolean
        get() = sharedPreferences.getBoolean("FlashState",false) // flash durumu
        set(value) = editor.putBoolean("FlashState",value).apply()

    var FlashStateExplanation : String?
        get() = sharedPreferences.getString("FlashStateExplanation","")
        set(value) = editor.putString("FlashStateExplanation",value).apply()

    var ScrenBrightness : Boolean
        get() = sharedPreferences.getBoolean("ScrenBrightness",false)
        set(value) = editor.putBoolean("ScrenBrightness",value).apply()

    var Sensor : Boolean
        get() = sharedPreferences.getBoolean("Sensor",false)
        set(value) = editor.putBoolean("Sensor",value).apply()
    var SensorY : Boolean
        get() = sharedPreferences.getBoolean("SensorY",false) // Yakınlık
        set(value) = editor.putBoolean("SensorY",value).apply()
    var SensorU : Boolean
        get() = sharedPreferences.getBoolean("SensorU",false) // Uzaklık
        set(value) = editor.putBoolean("SensorU",value).apply()

    var BackCamera : Boolean
        get() = sharedPreferences.getBoolean("BackCamera",false) // Uzaklık
        set(value) = editor.putBoolean("BackCamera",value).apply()
    var frontCamera : Boolean
        get() = sharedPreferences.getBoolean("frontCamera",false) // Uzaklık
        set(value) = editor.putBoolean("frontCamera",value).apply()

    var imeiInfo : String?
        get() = sharedPreferences.getString("imeiInfo","")
        set(value) = editor.putString("imeiInfo",value).apply()
}