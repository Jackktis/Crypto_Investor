package com.example.cryptoinvestor.view.fragments
// fået inspiration fra https://medium.com/@yilmazvolkan/kotlinlinecharts-c2a730226ff1 til hvordan,
// Man sætter en chart op i kotlin.

import android.graphics.Color.red
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.databinding.FragmentInfoCryptoBinding
import com.example.cryptoinvestor.databinding.FragmentCryptoBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_info_crypto.*
import kotlinx.android.synthetic.main.fragment_info_crypto.view.*

class InfoCryptoFragment : Fragment() {

    private lateinit var binding: FragmentInfoCryptoBinding

    companion object{
        fun newInstance() = InfoCryptoFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInfoCryptoBinding.inflate(inflater, container, false)

        return binding.root
    }

    }

    fun setLineChartData(lineChart: LineChart) {


        // x-værdier
        /*
        val xValue = ArrayList<String>()
        xValue.add("11.00")
        xValue.add("12.00")
        xValue.add("13.00")
        xValue.add("14.00")
        xValue.add("15.00")
*/
        // y-værdier

        val entries = ArrayList<Entry>()
        entries.add(Entry(20f, 0F))
        entries.add(Entry(50f, 1F))
        entries.add(Entry(60f, 2F))
        entries.add(Entry(70f, 3F))
        entries.add(Entry(80f, 4F))


        // datasættet for line
        val lineDataSet = LineDataSet(entries, "first")

        // DrawValue sættes false da en corresponding value i en chart vil i længden blive rodet
        // DrawFilled sættes til true for at fylde ud under grafen
        // linewidth kan vi ændre tykkelsen og fylde dens farve

        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 3f
        lineDataSet.fillColor = R.color.ThirdColor
        lineDataSet.fillAlpha = R.color.PrimaryColor

        //Her sættes en rotation angle til x-aksen (måske udkommenteres)
        //lineChart.xAxis.labelRotationAngle = 0f

        // hver sætter vi vores datasæt ind i en line chart til at tegnes

        lineChart.data = LineData(lineDataSet)

        // Dette kan bruges, hvis der ikke kommer noget data ind
        lineChart.description.text = "x-axis"
        lineChart.setNoDataText("No for x-axis yet!")

        // denne burde gerne loade dataen ind, undervejs, det virker kun for API 11
        lineChart.animateX(1800, Easing.EaseInExpo)


        //TODO: dette vil være en dot du kan hive rundt på charten for at se specifik værdi.
        // hvis vi gerne vil se hvordan, kan vi bruge https://medium.com/@yilmazvolkan/kotlinlinecharts-c2a730226ff1
        /*
        val markerView = CustomMarker(this@ShowForexActivity, R.layout.marker_view)
        lineChart.marker = markerView
         */

    }
