package com.berkaytok66.testapp.SuccsesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.berkaytok66.testapp.R
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class CustomScannerActivity : AppCompatActivity() {
    private lateinit var captureManager: CaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scanner)

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        textView = findViewById(R.id.qrCodeScannerText)

        // CaptureManager ile QR kod okuma işlemlerini yönetin
        captureManager = CaptureManager(this, barcodeScannerView)
        captureManager.initializeFromIntent(intent, savedInstanceState)
        captureManager.decode()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }
}