package com.berkaytok66.testapp.interfa

class VolumeKeyHandler(private val onKeyPressed: (Int) -> Unit) : VolumeKeyListener {

    override fun onVolumeKeyPressed(keyCode: Int): Boolean {
        onKeyPressed(keyCode)
        return true
    }
}