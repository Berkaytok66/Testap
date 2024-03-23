package com.berkaytok66.testapp.Companent

import android.app.Activity
import android.content.Context
import android.content.Intent

class StartActivity {

    companion object {
        fun launchActivity(fromActivity: Activity, toActivityClass: Class<*>) {
            val intent = Intent(fromActivity, toActivityClass)
            fromActivity.startActivity(intent)
            fromActivity.finish()
        }
    }
}
