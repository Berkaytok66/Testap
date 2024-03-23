package com.berkaytok66.testapp.Fragment.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkaytok66.testapp.Activity.Battery.BatteryActivity
import com.berkaytok66.testapp.Activity.Bluetooth.BluetoothActivity
import com.berkaytok66.testapp.Activity.CameraActivity.CameraActivity
import com.berkaytok66.testapp.Activity.EkranActivity.EkranActivity
import com.berkaytok66.testapp.Activity.MicTestActivity.MicTestActivity
import com.berkaytok66.testapp.Activity.OtoActivity
import com.berkaytok66.testapp.Activity.PixelActivity.PixelActivity
import com.berkaytok66.testapp.Activity.ProximitySensor.BrightnessActivity
import com.berkaytok66.testapp.Activity.ProximitySensor.ProximitySensorActivity
import com.berkaytok66.testapp.Activity.ProximitySensor.ScreenBrightness
import com.berkaytok66.testapp.Activity.SimSlot.SimSlotActivity
import com.berkaytok66.testapp.Activity.SoundActivity.SoundActivity
import com.berkaytok66.testapp.Activity.VibrationActivity.VibrationActivity
import com.berkaytok66.testapp.Activity.WifiPage.WifiActivity
import com.berkaytok66.testapp.Class.GenelTutucu
import com.berkaytok66.testapp.R



class CustomAdapter(private val dataSet: List<Item>, val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageViewIcon : ImageView
        val imageViewNext : ImageView

        init {

            textView = view.findViewById(R.id.center_text)
            imageViewIcon = view.findViewById(R.id.left_icon)
            imageViewNext = view.findViewById(R.id.right_icon)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.manuel_fragment_recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = dataSet[position]
        viewHolder.textView.text = item.text
        viewHolder.imageViewIcon.setImageResource(item.iconResId)
        viewHolder.imageViewNext.setImageResource(item.nextIcon)
        viewHolder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    val intent = Intent(context, OtoActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }

                1 -> {
                    val intent = Intent(context, WifiActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }

                2 -> {
                    val intent = Intent(context, BluetoothActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }

                3 -> {
                    val intent = Intent(context, SimSlotActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                4-> {
                    val intent = Intent(context, BatteryActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                5-> {
                    val intent = Intent(context, SoundActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                6-> {
                    val intent = Intent(context, MicTestActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                7-> {
                    val intent = Intent(context, VibrationActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                8-> {
                    val intent = Intent(context, EkranActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                9-> {
                    val intent = Intent(context, PixelActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                10-> {
                    val intent = Intent(context, ProximitySensorActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                11-> {
                    val intent = Intent(context, ScreenBrightness::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                12 -> {
                    val intent = Intent(context, BrightnessActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }
                13 -> {
                    val intent = Intent(context, CameraActivity::class.java)
                    context.startActivity(intent)
                    GenelTutucu.ActivityNext = true
                }



            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
