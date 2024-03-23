package com.berkaytok66.testapp.Activity.SoundActivity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.berkaytok66.testapp.Activity.MicTestActivity.MicTestActivity
import com.berkaytok66.testapp.Activity.SimSlot.SimSlotActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.CustomAlertDialogCompanent
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

class SoundActivity : AppCompatActivity() {
    private lateinit var countdownText: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var realtiveLayout :RelativeLayout
    private lateinit var linearLayout :LinearLayout
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager
    private lateinit var ahize: ImageView
    private lateinit var TopspeakerImege: ImageView
    private lateinit var BottomspeakerImege2: ImageView
    val handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)
        // AudioManager ve MediaPlayer'ı başlat
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mediaPlayer = MediaPlayer.create(this, R.raw.rawmp)
        countdownText = findViewById(R.id.countdownText)
        realtiveLayout = findViewById(R.id.realtiveLayout)
        linearLayout = findViewById(R.id.linearLayout)

        ahize = findViewById(R.id.ahize)
        TopspeakerImege = findViewById(R.id.speakerImege)
        BottomspeakerImege2 = findViewById(R.id.speakerImege2)
        setVolumeToMax()
        startCountdown()
    }
    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                countdownText.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                countdownText.text = "0"
                playSound()
                TopspeakerImege.visibility = View.VISIBLE
                BottomspeakerImege2.visibility = View.VISIBLE
               // realtiveLayout.visibility = View.INVISIBLE
                linearLayout.visibility = View.GONE
            }
        }
        countDownTimer.start()
    }
    private fun playSound() {

        val mediaPlayer = MediaPlayer.create(this, R.raw.notification)
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
        mediaPlayer.start()
        runnable = Runnable {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                mediaPlayer.release()
            }
            AlerdDialog()
        }
        handler.postDelayed(runnable!!, 7000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
        countDownTimer.cancel()
        countDownTimer.onFinish()
    }

    private fun setVolumeToMax() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
    }
    fun AlerdDialog() {
        val customDialog = CustomAlertDialogCompanent(
            context = this,
            title = "Hoparlör Testi",
            message = "Sesi Duydunuzmu ?",
            icon = R.mipmap.ic_launcher, // icon resource
            positiveButtonText = "Evet",
            negativeButtonText = "Hayır",
            positiveButtonListener = { dialog, which ->
                // Pozitif butona basıldığında yapılacak işlemler

                SharedPreferencesManager.HoparlorStatus = true
                playSoundThroughEarPiece()
                dialog.dismiss()
                TopspeakerImege.visibility = View.GONE
                BottomspeakerImege2.visibility = View.GONE
                ahize.visibility= View.VISIBLE
                //val BlActivite = Intent(this, SimSlotActivity::class.java)
                //startActivity(BlActivite)
                //finish()
            },
            negativeButtonListener = { dialog, which ->
                // Negatif butona basıldığında yapılacak işlemler
                SharedPreferencesManager.HoparlorStatus = false
                playSoundThroughEarPiece()
                TopspeakerImege.visibility = View.GONE
                BottomspeakerImege2.visibility = View.GONE
                ahize.visibility= View.VISIBLE
                dialog.dismiss()
            }
        )
        customDialog.show()
    }
    private fun playSoundThroughEarPiece() {
        val handler = Handler()
        // Hoparlörü kapat
        audioManager.isSpeakerphoneOn = false

        // Ahize hoparlörüne sesi yönlendir
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION

        // Ses çalma işlemi
        mediaPlayer.setOnCompletionListener {
            // Ses çalma işlemi bittikten sonra hoparlörü tekrar aç ve ses modunu normale döndür
            audioManager.isSpeakerphoneOn = true
            audioManager.mode = AudioManager.MODE_NORMAL
            it.release()


        }

        mediaPlayer.start()
        handler.postDelayed({
            mediaPlayer.pause()
            mediaPlayer.release()
            ahizeDialog()
        },7000)
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable!!)
        countDownTimer.cancel()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Testin tamamlanmasını bekleyin", Toast.LENGTH_SHORT).show()
    }
    fun ahizeDialog() {
        val customDialog = CustomAlertDialogCompanent(
            context = this,
            title = "Ahize Testi",
            message = "Ahize den ses aldınızmı ? ",
            icon = R.mipmap.ic_launcher, // icon resource
            positiveButtonText = "Evet",
            negativeButtonText = "Hayır",
            positiveButtonListener = { dialog, which ->
                // Pozitif butona basıldığında yapılacak işlemler

                SharedPreferencesManager.AhizeStatus = true
                countDownTimer.cancel()
                countDownTimer.onFinish()
                dialog.dismiss()
                startActivity()
            },
            negativeButtonListener = { dialog, which ->
                // Negatif butona basıldığında yapılacak işlemler
                SharedPreferencesManager.HoparlorStatus = false
                countDownTimer.cancel()
                countDownTimer.onFinish()
                dialog.dismiss()
                startActivity()
            }
        )
        customDialog.show()
    }
    fun startActivity(){
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this,MainActivity::class.java)
        }else{
            StartActivity.launchActivity(this, MicTestActivity::class.java)
        }

    }
}