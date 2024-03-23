package com.berkaytok66.testapp.Activity.VibrationActivity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import com.berkaytok66.testapp.Activity.EkranActivity.EkranActivity
import com.berkaytok66.testapp.Activity.SimSlot.SimSlotActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.CustomAlertDialogCompanent
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class VibrationActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var tvCountdown: TextView
    val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vibration)
        imageView = findViewById(R.id.imageView9)
        textView = findViewById(R.id.textView5)
        tvCountdown = findViewById(R.id.tvCountdown)

        // 10 saniye sonra Toast mesajını göster
        startCountdown()
    }
    private fun startCountdown() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val count = (millisUntilFinished / 1000).toInt()
                tvCountdown.text = count.toString()

                // Animasyon: TextView boyutunu büyüt ve küçült
                val scaleUp = ObjectAnimator.ofFloat(tvCountdown, "scaleX", 1.5f)
                val scaleDown = ObjectAnimator.ofFloat(tvCountdown, "scaleX", 1f)
                scaleUp.interpolator = AccelerateDecelerateInterpolator()
                scaleDown.interpolator = AccelerateDecelerateInterpolator()
                scaleUp.duration = 500
                scaleDown.duration = 500
                scaleUp.start()
                scaleUp.doOnEnd { scaleDown.start() }
            }

            override fun onFinish() {
                tvCountdown.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                textView.visibility=View.GONE
                // Sayım bittiğinde yapılacak işlem
                vibrateDeviceWithAnimation(this@VibrationActivity, imageView)
            }
        }.start()
    }
    fun vibrateDeviceWithAnimation(context: Context, imageView: ImageView) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)

        imageView.startAnimation(shakeAnimation)

        // Titreşimi başlat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // API 26 ve üzeri için
            val vibrationEffect =
                VibrationEffect.createOneShot(10000, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            // Eski API sürümleri için
            vibrator.vibrate(10000)
        }

        // 10 saniye sonra titreşimi ve animasyonu durdur
        runnable = Runnable {
            imageView.clearAnimation() // Animasyonu durdur
            vibrator.cancel() // Titreşimi durdur
            showMesage()
        }
        handler.postDelayed(runnable,10000)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        imageView.clearAnimation() // Animasyonu durdur
    }
    fun showMesage() {
        val customDialog = CustomAlertDialogCompanent(
            context = this,
            title = "Titreşim Testi",
            message = "Cihazınız titreşim eylemi gerçekleştirdimi ?",
            icon = R.mipmap.ic_launcher, // icon resource
            positiveButtonText = "Evet",
            negativeButtonText = "Hayır",
            positiveButtonListener = { dialog, which ->
                // Pozitif butona basıldığında yapılacak işlemler

                SharedPreferencesManager.VibrationState = true
                startActivity()
            },
            negativeButtonListener = { dialog, which ->
                // Negatif butona basıldığında yapılacak işlemler
                SharedPreferencesManager.VibrationState = false
                startActivity()
                dialog.dismiss()
            }
        )
        customDialog.show()
    }

    fun startActivity() {
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this, MainActivity::class.java)
            finish()
        }else{
            StartActivity.launchActivity(this, EkranActivity::class.java)
            finish()
        }

    }
}