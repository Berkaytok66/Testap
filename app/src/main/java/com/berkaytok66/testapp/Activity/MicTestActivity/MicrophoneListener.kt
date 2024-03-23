// MicrophoneListener.kt
package com.berkaytok66.testapp.Activity.MicTestActivity

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlin.concurrent.thread
import kotlin.math.sqrt

class MicrophoneListener(private val onAudioLevelUpdate: (Float) -> Unit) {
    private var isListening = false
    private lateinit var audioRecord: AudioRecord
    private val sampleRate = 44100 // CD kalitesinde
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)

    @SuppressLint("MissingPermission")
    fun startListening() {
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize)
        audioRecord.startRecording()
        isListening = true

        thread {
            val audioData = ShortArray(bufferSize)
            while (isListening) {
                val readSize = audioRecord.read(audioData, 0, bufferSize)

                if (readSize > 0) {
                    // Burada ses verilerini işleyebilirsiniz.
                    val level = calculateLevel(audioData, readSize)
                    onAudioLevelUpdate(level)
                }
            }
        }
    }

    fun stopListening() {
        isListening = false
        audioRecord.stop()
        audioRecord.release()
    }

    private fun calculateLevel(buffer: ShortArray, readSize: Int): Float {
        var sum = 0.0
        for (i in 0 until readSize) {
            sum += buffer[i] * buffer[i]
        }
        return sqrt(sum / readSize).toFloat()
    }
}
