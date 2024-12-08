package com.example.vico

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.vico.databinding.Chart4Binding
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.launch
import kotlin.random.Random

class Chart4Activity : AppCompatActivity() {
    private lateinit var binding: Chart4Binding
    private val modelProducer = CartesianChartModelProducer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Chart4Binding.inflate(layoutInflater) // Initialize binding
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
                            2 +
                                    Random.nextFloat() * 18
                        }
                    )
                }
            }
                /* Learn more:
                https://patrykandpatrick.com/vico/wiki/cartesian-charts/layers/line-layer#data. */
                lineSeries { series(List(50) { Random.nextFloat() * 20 }) } }
        }
    }
}