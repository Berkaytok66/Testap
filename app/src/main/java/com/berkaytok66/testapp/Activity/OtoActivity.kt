package com.berkaytok66.testapp.Activity

import com.berkaytok66.testapp.Activity.WifiPage.WifiActivity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.widget.TextView

import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.CustomAlertDialogCompanent
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R
import com.berkaytok66.testapp.interfa.VolumeKeyHandler

class OtoActivity : AppCompatActivity() {
    private var textVolumeUp: TextView? = null
    private var textVolumeDown: TextView? = null
    private var textVolumeSuccsesUp: TextView? = null
    private var textVolumeSuccsesDown: TextView? = null
    private var isVolumeUpPressed = false
    private var isVolumeDownPressed = false
    private lateinit var volumeKeyHandler: VolumeKeyHandler
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oto)
        textVolumeUp = findViewById(R.id.textVolumeUp)
        textVolumeDown = findViewById(R.id.txtVolumeDown)
        textVolumeSuccsesUp = findViewById(R.id.textVolumeUpSuccses)
        textVolumeSuccsesDown = findViewById(R.id.tvVolumeDownResult)

        volumeKeyHandler = VolumeKeyHandler { keyCode ->
            handleVolumeKeyPress(keyCode)
        }
        startCountdown()


    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                isVolumeUpPressed = true
                startCountdown()
                // Ses artırma tuşuna basıldığında yapılacak işlemler
                handleVolumeKeyPress(keyCode)
                checkBothKeysPressed()
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                isVolumeDownPressed = true
                startCountdown()
                // Ses azaltma tuşuna basıldığında yapılacak işlemler
                handleVolumeKeyPress(keyCode)
                checkBothKeysPressed()
                return true
            }
            else -> return super.onKeyDown(keyCode, event)
        }
    }
    private fun handleVolumeKeyPress(keyCode: Int) {
        // Ses tuşlarına basıldığında yapılacak işlemler

        if (isVolumeUpPressed) {
            textVolumeSuccsesUp?.visibility = View.VISIBLE
            textVolumeSuccsesUp?.text = "Başarılı"
           // GenelTutucu.VolumeUp = isVolumeUpPressed
            SharedPreferencesManager.VolumeUp = isVolumeUpPressed
        }

        if (isVolumeDownPressed) {
            textVolumeSuccsesDown?.visibility = View.VISIBLE
            textVolumeSuccsesDown?.text = "Başarılı"
         //   GenelTutucu.VolumeDown = isVolumeDownPressed
            SharedPreferencesManager.VolumeDown = isVolumeDownPressed

        }


    }
    private fun checkBothKeysPressed() {
        if (isVolumeUpPressed && isVolumeDownPressed) {
            // Timer'ı iptal et
            countDownTimer?.cancel()
            // Her iki tuşa da basılmışsa, daha fazla işlem yapma
            if (GenelTutucu.ActivityNext){
                StartActivity.launchActivity(this,MainActivity::class.java)

            }else{
                val wifiActivty = Intent(this, WifiActivity::class.java)
                startActivity(wifiActivty)
                finish()

            }


        }
        if (!isVolumeUpPressed && !isVolumeDownPressed) {
            if (GenelTutucu.ActivityNext){
                StartActivity.launchActivity(this,MainActivity::class.java)

            }else{
                val wifiActivty = Intent(this, WifiActivity::class.java)
                startActivity(wifiActivty)
                finish()

            }
        }
    }
    private fun startCountdown() {
        // Mevcut timer'ı iptal et
        countDownTimer?.cancel()

        // Yeni bir timer başlat
        countDownTimer = object : CountDownTimer(10000, 10000) {
            override fun onTick(millisUntilFinished: Long) {
                // Bu örnekte gerekli değil
            }

            override fun onFinish() {
                if (!isVolumeUpPressed || !isVolumeDownPressed) {

                    val customDialog = CustomAlertDialogCompanent(
                        context = this@OtoActivity,
                        title = "Tuş Kontrol",
                        message = "Ses ( + ) ve ya Ses ( - ) Tuşlarına temas algılanamadı! Raporlamak istermisiniz? ",
                        icon = R.mipmap.ic_launcher, // icon resource
                        positiveButtonText = "Evet Rapor Al",
                        negativeButtonText = "Hayır Tekrar Deneyeceğim",
                        positiveButtonListener = DialogInterface.OnClickListener { dialog, which ->
                            // Pozitif butona basıldığında yapılacak işlemler

                            if (!isVolumeUpPressed && !isVolumeDownPressed){
                              //  GenelTutucu.VolumeDown= false
                              //  GenelTutucu.VolumeUp=false
                                SharedPreferencesManager.VolumeUp = false
                                SharedPreferencesManager.VolumeDown = false
                                checkBothKeysPressed()
                            }


                        },
                        negativeButtonListener = DialogInterface.OnClickListener { dialog, which ->
                            // Negatif butona basıldığında yapılacak işlemler
                            startCountdown()
                            dialog.dismiss()
                        }
                    )

                    customDialog.show()

                }
                // Timer sona erdikten sonra değişkenleri sıfırla
                isVolumeUpPressed = false
                isVolumeDownPressed = false
            }
        }.start()
    }

}