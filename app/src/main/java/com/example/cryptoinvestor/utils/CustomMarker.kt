import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.chart.view.*

/*
    Modified from the guide:
    https://medium.com/@yilmazvolkan/kotlinlinecharts-c2a730226ff1
 */
class CustomMarker(context: Context, layoutResource: Int):  MarkerView(context, layoutResource) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        val value = entry?.y?.toDouble() ?: 0.0
        val resText: String = if(value.toString().length > 8){
            "Val: " + value.toString().substring(0,7)
        } else{
            "Val: $value"
        }
        tvCurrencyPrice.text = resText
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }
}