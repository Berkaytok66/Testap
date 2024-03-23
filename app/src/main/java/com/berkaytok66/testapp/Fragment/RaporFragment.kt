package com.berkaytok66.testapp.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.berkaytok66.testapp.R
import com.berkaytok66.testapp.SuccsesActivity.SuccsesActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration


class RaporFragment : Fragment() {

    lateinit var get_rapor_btn: Button
    lateinit var satis_sozlesmesi_olustur_BTN: Button
    lateinit var devices_name : TextView
    lateinit var modelText : TextView
    lateinit var androidVersion : TextView
    lateinit var cihazID : TextView
    lateinit var productName : TextView

    private var adView: AdView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? { var view = inflater.inflate(R.layout.fragment_rapor, container, false)

        get_rapor_btn = view.findViewById(R.id.btn)
        satis_sozlesmesi_olustur_BTN = view.findViewById(R.id.satis_sozlesmesi_olustur_BTN)
        devices_name = view.findViewById(R.id.devices_name)
        modelText = view.findViewById(R.id.model)
        androidVersion = view.findViewById(R.id.androidVersionBilgisi)
        cihazID = view.findViewById(R.id.cihazID)
        productName = view.findViewById(R.id.productName)
        cihaz_info()

        get_rapor_btn.setOnClickListener {
            var intent = Intent(view.context,SuccsesActivity::class.java)
            startActivity(intent)
        }
        MobileAds.initialize(view.context){}

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("PLACE_TEST_DEVICE_ID_1_HERE","PLACE_TEST_DEVICE_ID_2_HERE")).build()
        )
        adView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
        adView?.adListener = object : AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()

            }

            override fun onAdClosed() {
                super.onAdClosed()

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)

            }

            override fun onAdImpression() {
                super.onAdImpression()

            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }

            override fun onAdOpened() {
                super.onAdOpened()

            }

            override fun onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked()

            }
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    fun cihaz_info(){

        val uretici = Build.MANUFACTURER // uretici
        val model = Build.MODEL // model
        val urun = Build.PRODUCT
        val osVersiyonu = Build.VERSION.RELEASE // android
        val androidId = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
        devices_name.setText(": $uretici")
        modelText.setText(": $model")
        productName.setText(": $urun")
        androidVersion.setText(": $osVersiyonu")
        cihazID.setText(": $androidId")



    }
    override fun onPause() {
        adView?.pause()
        super.onPause()

    }

    override fun onResume() {
        adView?.resume()
        super.onResume()

    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()

    }
}