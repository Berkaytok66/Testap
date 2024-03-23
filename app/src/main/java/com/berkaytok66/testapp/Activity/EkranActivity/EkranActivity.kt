package com.berkaytok66.testapp.Activity.EkranActivity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.Toast
import com.berkaytok66.testapp.Activity.PixelActivity.PixelActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R

@Suppress("DEPRECATION")
class EkranActivity : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var isWhite: BooleanArray
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekran)
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or // Tam ekran
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or // Navigasyon çubuğunu gizle
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or // Daldırıcı mod
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or // Navigasyon çubuğu için layout
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Bildirim çubuğu için layout
        }

        // Kamera çentiği ve bildirim alanını kapatan diğer ayarlar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }
        gridView = findViewById(R.id.gridView)
        val numItems = 60// Karelerin sayısı
        isWhite = BooleanArray(numItems) { false }

        gridView.adapter = object : BaseAdapter() {
            override fun getCount(): Int = isWhite.size

            override fun getItem(position: Int): Any = isWhite[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = View(this@EkranActivity)
                // Kareler için AbsListView.LayoutParams kullanın
                view.layoutParams = AbsListView.LayoutParams(190, 190) // Kare boyutları
                view.setBackgroundColor(if (isWhite[position]) 0xFFFFFFFF.toInt() else 0xFF0000FF.toInt())
                return view
            }
        }
        gridView.setOnTouchListener { _, event ->
            val position = gridView.pointToPosition(event.x.toInt(), event.y.toInt())
            if (position >= 0) {
                isWhite[position] = true
                gridView.invalidateViews()
                if (isWhite.all { it }) {
                    //tüm kareler beyaz olunca activity geç
                    SharedPreferencesManager.ScreenState = true
                    StartActivity()
                    handler.removeCallbacks(runnable)
                }
            }
            true
        }

        // 30 saniye sonra kontrol
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            val notWhiteIndices = isWhite.mapIndexed { index, isWhite ->
                if (!isWhite) index else null
            }.filterNotNull().joinToString(", ")

            if (notWhiteIndices.isNotEmpty()) {
                //30 sn sonunda boyanmayan kısımlar
                SharedPreferencesManager.ScreenError = notWhiteIndices
                StartActivity()
            } else {
                //Tüm kareler beyaz 30 sn sonunda
                SharedPreferencesManager.ScreenState = true
                StartActivity()
            }
        }
        handler.postDelayed(runnable, 30000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    fun StartActivity(){
        if (GenelTutucu.ActivityNext){
            StartActivity.launchActivity(this,MainActivity::class.java)
            finish()
        }else{
            StartActivity.launchActivity(this,PixelActivity::class.java)
            finish()
        }

    }
}