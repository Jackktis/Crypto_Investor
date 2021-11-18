package com.example.cryptoinvestor.view.fragments
// fået inspiration fra https://medium.com/@yilmazvolkan/kotlinlinecharts-c2a730226ff1 til hvordan,
// Man sætter en chart op i kotlin.

import android.graphics.Color
import android.graphics.Color.red
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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

import androidx.navigation.findNavController
import com.example.cryptoinvestor.R
import com.example.cryptoinvestor.di.ServiceLocator.cryptoViewModel
import com.example.cryptoinvestor.di.ServiceLocator.infoCryptoViewModel
import com.example.cryptoinvestor.utils.FLOAT_FORMATTER
import com.example.cryptoinvestor.utils.PRICE_FORMATTER
import com.example.cryptoinvestor.viewmodel.InfoCryptoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.currency_list.view.*


class InfoCryptoFragment : Fragment() {

    private lateinit var binding: FragmentInfoCryptoBinding
    private val viewModel by lazy { infoCryptoViewModel }
    var id : String = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (bundle != null) {
            id = bundle.getString("id").toString()
            viewModel.refreshAsset(id)


            viewModel.asset.observe(viewLifecycleOwner, {asset ->
                if(asset.isSuccessful){
                    asset.body()?.let {
                        var imageUrl = "https://static.coincap.io/assets/icons/"
                        view.CurrencyFullName.text = it.name
                        view.CurrencyInti.text = it.symbol
                        view.Currencies_price.text = PRICE_FORMATTER.format(it.price).toString()
                        var changeTxt = FLOAT_FORMATTER.format(it.change24Hr).toString()
                        println(asset.body()?.toString())
                        view.info_changePr24Hr.text = changeTxt
                        Picasso.get().load(imageUrl+it.symbol.lowercase()+"@2x.png").into(view.info_CurrencyImage)
                        if (changeTxt.contains("-")){
                            Log.w("Negativ", changeTxt)
                            view.info_changePr24Hr.setTextColor(Color.RED)
                            view.procent.setTextColor(Color.RED)
                        }else{
                            Log.w("Positiv", changeTxt)
                            view.info_changePr24Hr.setTextColor(Color.GREEN)
                            view.procent.setTextColor(Color.GREEN)
                        }

                    }
                }
            })
        }


    }

    override fun onDestroy() {
        super.onDestroy()
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
}

