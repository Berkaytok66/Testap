package com.berkaytok66.testapp.Activity.PixelActivity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.berkaytok66.testapp.Activity.ProximitySensor.ProximitySensorActivity
import com.berkaytok66.testapp.Activity.ProximitySensor.ScreenBrightness
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

@Suppress("DEPRECATION")
class PixelActivity : AppCompatActivity() {
    private lateinit var countdownText: TextView
    private lateinit var tText: TextView
    private lateinit var constraintLayout: ConstraintLayout
    private var countDownTimer: CountDownTimer? = null
    private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    private var currentColorIndex = 0
    private var colorChangeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixel)

        countdownText = findViewById(R.id.textView213)
        tText = findViewById(R.id.textView3)
        constraintLayout = findViewById(R.id.constraintLayout)


        setupFullScreen()
        startCountDown()
    }

    private fun setupFullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }
    }

    private fun startCountDown() {
        val heartbeatAnimation = AnimationUtils.loadAnimation(this, R.anim.ritim)
        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownText.text = (millisUntilFinished / 1000).toString()
                countdownText.startAnimation(heartbeatAnimation)
            }

            override fun onFinish() {
                countdownText.visibility = View.INVISIBLE
                tText.visibility = View.INVISIBLE
                constraintLayout.setBackgroundColor(Color.RED)
                constraintLayout.setOnClickListener {
                    showAlertDialog()
                }
            }
        }
        countDownTimer?.start()
    }
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel() // Timer'ı durdur
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
    private fun showAlertDialog() {
        val colorName = when (colors[currentColorIndex]) {
            Color.RED -> "Kırmızı"
            Color.GREEN -> "Yeşil"
            Color.BLUE -> "Mavi"
            Color.YELLOW -> "Sarı"
            // Diğer renkler için benzer durumlar ekleyebilirsiniz
            else -> "Bilinmeyen Renk"
        }

        AlertDialog.Builder(this)
            .setTitle("$colorName")
            .setMessage("$colorName Renginde pixel sorunu var mı?")
            .setPositiveButton("Hata Yok") { _, _ -> changeColor() }
            .setNegativeButton("Hata Var") { _, _ -> saveColorError(colorName) }
            .create()
            .show()
    }


    private fun changeColor() {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        constraintLayout.setBackgroundColor(colors[currentColorIndex])

        if (++colorChangeCount >= colors.size) {
            // Burada testin bitiş işlemlerini yapabilirsiniz
            if (GenelTutucu.ActivityNext){
                countDownTimer?.cancel() // Timer'ı durdur
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                countDownTimer?.cancel() // Timer'ı durdur
                val intent = Intent(this, ProximitySensorActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun saveColorError(colorName: String) {
        when (colorName) {
            "Kırmızı" -> SharedPreferencesManager.PixelColorRed = true
            "Yeşil" -> SharedPreferencesManager.PixelColorGreen = true
            "Mavi" -> SharedPreferencesManager.PixelColorBlue = true
            "Sarı" -> SharedPreferencesManager.PixelColorYellow = true
            // Diğer renkler için benzer durumlar ekleyebilirsiniz
        }
        changeColor()
    }
}
