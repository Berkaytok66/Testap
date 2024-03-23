import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class SoundLevelView(context: Context) : View(context) {
    private var levels = mutableListOf<Int>()

    private val linePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 10f
        isAntiAlias = true
    }

    fun updateLevels(level: Int) {
        if (levels.size > width / 12) { // Basit bir sınırlandırma, her bir bar 10px + 2px boşluk
            levels.removeAt(0)
        }
        levels.add(level)
        invalidate() // View'i yeniden çizmek için çağır
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val widthPerLevel = 12 // Her bir bar için genişlik
        for (i in levels.indices) {
            val left = i * widthPerLevel.toFloat()
            val right = left + 10
            val bottom = height.toFloat()
            val top = bottom - (levels[i] / 100f * bottom) // Ölçeğe göre yüksekliği hesapla
            canvas.drawRect(left, top, right, bottom, linePaint)
        }
    }
}
