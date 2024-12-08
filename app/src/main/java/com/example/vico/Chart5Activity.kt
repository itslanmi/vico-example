package com.example.vico

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.vico.databinding.Chart5Binding
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.CandlestickCartesianLayer
import com.patrykandpatrick.vico.core.common.Defaults
import kotlinx.coroutines.launch
import kotlin.random.Random

private val startAxisItemPlacer = VerticalAxis.ItemPlacer.count({ 3 })

class Chart5Activity : AppCompatActivity() {
    private lateinit var binding: Chart5Binding
    private val modelProducer = CartesianChartModelProducer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Chart5Binding.inflate(layoutInflater).apply {
            with(chartView){
                chart = chart?.copy(
                    startAxis = (chart?.startAxis as VerticalAxis).copy(itemPlacer = startAxisItemPlacer),
                )
                this.modelProducer = modelProducer
            }
        }

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.chartView.modelProducer = modelProducer
        lifecycleScope.launch {
            modelProducer.runTransaction { columnSeries {
                repeat(3) {
                    series(
                        List(50) {
                            2 + Random.nextFloat() * 18
                        }
                    )
                }
            } }
        }
    }
}