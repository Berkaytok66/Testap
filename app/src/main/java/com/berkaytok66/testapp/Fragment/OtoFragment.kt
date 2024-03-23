package com.berkaytok66.testapp.Fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.berkaytok66.testapp.Activity.OtoActivity
import com.berkaytok66.testapp.Activity.ProximitySensor.ScreenBrightness
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.R
import com.berkaytok66.testapp.SuccsesActivity.SuccsesActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class OtoFragment : Fragment() {
    private var button2: Button? = null
    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "OtoFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_oto, container, false)
        button2 = view.findViewById(R.id.pozitiveBNutton)
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(view.context, getString(R.string.otoTestGecisTestADS),adRequest,object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad: ${adError.message}")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Reklam yüklendi.")
                mInterstitialAd = interstitialAd
                setupAdCallbacks()
            }
        })

        button2?.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d(TAG, "Geçiş reklamı henüz yüklenmedi.")
                startActivity()
            }
        }
        return view
    }

    private fun setupAdCallbacks() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                //Reklam kapatıldığında veya gösterim tamamlandığında çağrılır, burada aktivite başlatılır.
                Log.d(TAG, "Reklam tam ekran içeriği reddetti.")
                startActivity()
                mInterstitialAd = null // Sonraki yükleme için null'a ayarla.
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                //Reklam gösterilmediğinde çağrılır.
                Log.e(TAG, "Reklam tam ekran içeriğini gösteremedi. Hata: ${adError.message}")
                mInterstitialAd = null
            }
        }
    }

    private fun startActivity(){
        val intent = Intent(view?.context, OtoActivity::class.java)
        startActivity(intent)
        GenelTutucu.ActivityNext = false
    }
}
