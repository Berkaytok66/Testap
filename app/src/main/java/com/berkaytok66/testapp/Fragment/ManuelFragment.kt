package com.berkaytok66.testapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkaytok66.testapp.Fragment.Adapter.CustomAdapter
import com.berkaytok66.testapp.Fragment.Adapter.Item
import com.berkaytok66.testapp.R

class ManuelFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val dataset = listOf(
        Item("Ses+ Ve Ses- Tuş Kontrolü", R.drawable.screen_control, R.drawable.next_icons),
        Item("Wifi Kontrol", R.drawable.wifi_icons, R.drawable.next_icons),
        Item("Bluetooth Kotrol", R.drawable.bluetooth, R.drawable.next_icons),
        Item("Sim Slot Kontrol", R.drawable.sim_slot, R.drawable.next_icons),
        Item("Batarya - Pil Kontrol", R.drawable.battery, R.drawable.next_icons),
        Item("Hoparlör ve Ahize", R.drawable.sound_control, R.drawable.next_icons),
        Item("Mikrafon Kontrol", R.drawable.mic_images, R.drawable.next_icons),
        Item("Titreşim Kontrol", R.drawable.vibration_control, R.drawable.next_icons),
        Item("Ekran Kontrol", R.drawable.screen_control, R.drawable.next_icons),
        Item("Pixel Kontrol", R.drawable.pixel_control, R.drawable.next_icons),
        Item("Yakınlık Sensörü", R.drawable.sensor_icons, R.drawable.next_icons),
        Item("Flash ışığı Kontrol", R.drawable.flash, R.drawable.next_icons),
        Item("Ekran Parlaklık Kontrol", R.drawable.ekran_parlak_icon, R.drawable.next_icons),
        Item("Kamera Kontrol", R.drawable.camera_icons, R.drawable.next_icons),
        // ... Diğer aylar ve ikonlar
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
        View? { var view = inflater.inflate(R.layout.fragment_manuel, container, false)
        recyclerView = view.findViewById(R.id.RecyclerView)

        val customAdapter = CustomAdapter(dataset,view.context)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }


}