package com.testinopenapp.ui.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.testinopenapp.R
import com.testinopenapp.data.NetworkResponse
import com.testinopenapp.data.model.dashboard.DashboardResponse
import com.testinopenapp.data.model.dashboard.TopLink
import com.testinopenapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar


@AndroidEntryPoint
class DashboardFragment : Fragment() {


    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val linksAdapter by lazy {
        LinkListAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding.greetingstv.text = getGreetingMessage()
        setObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        viewModel.dashboarddata.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    val links = response.result?.data?.top_links
                    linksAdapter.submitList(links?.toMutableList())
                    if (linksAdapter.currentList.isEmpty()) {
                        //binding.root.showSnack(getString(R.string.no_characters_found))
                        binding.progressBar.visibility = View.GONE

                    } else {
                        linksAdapter.submitList(links?.toMutableList())
                    }
                    response.result?.data?.overall_url_chart?.let { setupLineChartData(it) }
                    setupOtherData(response.result)
                }

                is NetworkResponse.Error -> {
                   // binding.root.showSnack(getString(R.string.check_internet_connection_txt))
                }
                NetworkResponse.Loading -> Unit
            }
            binding.rvLinks.adapter = linksAdapter
        }
    }

    private fun setupOtherData(data: DashboardResponse?) {
        binding.clickstv.text = data!!.today_clicks.toString()
        binding.locationtv.text = data.top_location
        binding.topsourcetv.text = data.top_source



        var isclicked = "btnTvTopLinks"

        binding.btnTvTopLinks.setOnClickListener {


            binding.btnTvTopLinks.setTextColor(Color.WHITE)
            binding.btnTvTopLinks.setBackgroundResource(R.drawable.tab_bg)


            binding.btnTvRecentLinks.setTextColor(-0x999CA0)
            binding.btnTvRecentLinks.setBackgroundResource(0)

            linksAdapter.submitList(data.data.top_links.toMutableList())



        }

        binding.btnTvRecentLinks.setOnClickListener {

            binding.btnTvRecentLinks.setTextColor(Color.WHITE)
            binding.btnTvRecentLinks.setBackgroundResource(R.drawable.tab_bg)

            binding.btnTvTopLinks.setTextColor(-0x999CA0)
            binding.btnTvTopLinks.setBackgroundResource(0)

            linksAdapter.submitList(data.data.recent_links.toMutableList())




        }


        binding.main2conshidden.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE


    }

    fun getGreetingMessage():String{
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hello"
        }
    }


    private fun setupLineChartData(overallUrlChart: Map<String, Int>) {
        val yVals = ArrayList<Entry>()



        for ((i,item) in overallUrlChart.entries.withIndex()){
            yVals.add(Entry(i.toFloat(),item.value.toFloat()))

        }


        val set1: LineDataSet
        set1 = LineDataSet(yVals, "DataSet 1")



        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.setDrawCircles(false)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(true)

        val yAxisRight: YAxis = binding.lineChart.getAxisRight()
        yAxisRight.isEnabled = false

        set1.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_graph)


        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data

        binding.lineChart.setData(data)
        binding.lineChart.description.isEnabled = false
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.setPinchZoom(true)
        binding.lineChart.xAxis.enableGridDashedLine(3f, 3f, 0f)
        binding.lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
       // binding.lineChart.xAxis.labelCount = 11
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        val xAxis: XAxis = binding.lineChart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.setGranularityEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
       // xAxis.labelCount = getXAxisValues(overallUrlChart).size // important
        xAxis.setSpaceMax(0.5f) // optional
        xAxis.setSpaceMin(0.5f) // optional


        xAxis.valueFormatter = object : ValueFormatter() {
            override
            fun getFormattedValue(value: Float): String {
                // value is x as index
                val labels = ArrayList<String>()

                for (item in overallUrlChart){
                    val date = SimpleDateFormat("yyyy-MM-dd").parse(item.key.toString())

                    val formatterMonth = SimpleDateFormat("MMM dd")
                    val currentMonth = formatterMonth.format(date)

                    println(currentMonth)

                    labels.add(currentMonth)
                }

               // enddate = labels.get(labels.size-1).toString()

                return labels[value.toInt()]
            }
        }



        //xAxis.setValueFormatter(IndexAxisValueFormatter(getXAxisValues(overallUrlChart)));


        binding.lineChart.invalidate();
        binding.lineChart.refreshDrawableState();





        val startdate = SimpleDateFormat("yyyy-MM-dd").parse(overallUrlChart.entries.toList().get(0).key.toString())
        val enddate = SimpleDateFormat("yyyy-MM-dd").parse(overallUrlChart.entries.toList().get(overallUrlChart.size-1).key.toString())

        val formatterMonth = SimpleDateFormat("dd MMM")

        val sd = formatterMonth.format(startdate)
        val ed = formatterMonth.format(enddate)



        binding.daterangetv.text = sd+" - "+ed


    }

    // creating list of x-axis values
    private fun getXAxisValues(overallUrlChart: Map<String, Int>): ArrayList<String> {
        val labels = ArrayList<String>()

        for (item in overallUrlChart){
            val date = SimpleDateFormat("yyyy-MM-dd").parse(item.key.toString())
            labels.add(date.month.toString())
        }

        return labels
    }

}