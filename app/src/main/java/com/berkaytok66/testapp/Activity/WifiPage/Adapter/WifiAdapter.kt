import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.widget.Toast
import com.berkaytok66.testapp.R

class WifiAdapter(private val wifiList: List<ScanResult>) : RecyclerView.Adapter<WifiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ssidTextView: TextView = view.findViewById(R.id.ssidTextView)
        val signalImageView: ImageView = view.findViewById(R.id.signalImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wifi_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wifiNetwork = wifiList[position]
        holder.ssidTextView.text = wifiNetwork.SSID
        println(wifiNetwork.SSID.length)

        // Sinyal gücüne göre bir gösterge belirleme
        val signalStrength = WifiManager.calculateSignalLevel(wifiNetwork.level, 4)
        when (signalStrength) {
            0 -> holder.signalImageView.setImageResource(R.drawable.wifi_2)
            1 -> holder.signalImageView.setImageResource(R.drawable.wifi_3)
            2 -> holder.signalImageView.setImageResource(R.drawable.wifi_4)
            3 -> holder.signalImageView.setImageResource(R.drawable.wifi_5)

            else -> holder.signalImageView.setImageResource(R.drawable.wifi_1)
        }
    }

    override fun getItemCount() = wifiList.size
}
