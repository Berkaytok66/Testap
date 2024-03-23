package com.berkaytok66.testapp.Activity.CameraActivity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.Class.SharedPreferencesManager
import com.berkaytok66.testapp.Companent.StartActivity
import com.berkaytok66.testapp.MainActivity
import com.berkaytok66.testapp.R
import com.berkaytok66.testapp.SuccsesActivity.SuccsesActivity

class CameraActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private lateinit var cameraManager: CameraManager
    private lateinit var imageButton: ImageButton
    private lateinit var imageButton2: ImageButton
    private var cameraDevice: CameraDevice? = null
    private var isFrontCamera = false
    private var cameraCaptureSessions: CameraCaptureSession? = null
    private lateinit var captureRequestBuilder: CaptureRequest.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        textureView = findViewById(R.id.textureView)
        imageButton = findViewById(R.id.imageButton)
        imageButton2 = findViewById(R.id.imageButton2)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)

        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
        imageButton.setOnClickListener {
            if (cameraDevice != null) {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Arka kamera")
                builder.setMessage("Arka Kamerada Görüntü varmı?")
                builder.setPositiveButton("Evet") { dialog, which ->
                    isFrontCamera = !isFrontCamera
                    cameraDevice?.close()
                    SharedPreferencesManager.BackCamera = true

                    openCamera()



                    imageButton.visibility = View.GONE
                    imageButton2.visibility = View.VISIBLE
                }
                builder.setNegativeButton("Hayır") { dialog, which ->
                    isFrontCamera = !isFrontCamera
                    cameraDevice?.close()
                    SharedPreferencesManager.BackCamera = false

                    openCamera()



                    imageButton.visibility = View.GONE
                    imageButton2.visibility = View.VISIBLE
                }

                builder.setNeutralButton("Tekrar Bak") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()


            }

        }
        imageButton2.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Ön kamera")
            builder.setMessage("Ön Kamerada Görüntü varmı?")
            builder.setPositiveButton("Evet") { dialog, which ->
                isFrontCamera = !isFrontCamera
                cameraDevice?.close()
                SharedPreferencesManager.frontCamera = true
                dialog.dismiss()
                if (GenelTutucu.ActivityNext){
                    StartActivity.launchActivity(this,MainActivity::class.java)
                }else{
                    StartActivity.launchActivity(this,SuccsesActivity::class.java)
                }





            }
            builder.setNegativeButton("Hayır") { dialog, which ->
                isFrontCamera = !isFrontCamera
                cameraDevice?.close()
                SharedPreferencesManager.frontCamera = false
                dialog.dismiss()
                if (GenelTutucu.ActivityNext){
                    StartActivity.launchActivity(this,MainActivity::class.java)
                }else{
                    StartActivity.launchActivity(this,SuccsesActivity::class.java)
                }
            }

            builder.setNeutralButton("Tekrar Bak") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()

        }

    }
    private fun openCamera() {
        try {
            for (cameraId in cameraManager.cameraIdList) {
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)
                val cameraFacing = characteristics.get(CameraCharacteristics.LENS_FACING)

                if (isFrontCamera && cameraFacing == CameraCharacteristics.LENS_FACING_FRONT) {
                    openCameraById(cameraId)
                    return
                } else if (!isFrontCamera && cameraFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    openCameraById(cameraId)
                    return
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun openCameraById(cameraId: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                createCameraPreviewSession()
            }

            override fun onDisconnected(camera: CameraDevice) {
                camera.close()
            }

            override fun onError(camera: CameraDevice, error: Int) {
                camera.close()
                cameraDevice = null
            }
        }, null)
    }
    private fun createCameraPreviewSession() {
        try {
            val texture = textureView.surfaceTexture
            texture?.setDefaultBufferSize(640, 480)
            val surface = Surface(texture)

            captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)

            cameraDevice?.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    cameraCaptureSessions = session
                    updatePreview()
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {}
            }, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun updatePreview() {
        if (cameraDevice == null) return
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder.build(), null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        cameraDevice?.close()
    }
}