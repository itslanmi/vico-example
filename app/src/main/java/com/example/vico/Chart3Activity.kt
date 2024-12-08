package com.example.vico

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.vico.databinding.Chart3Binding
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.RandomCartesianModelGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
class ChartViewModel : ViewModel() {
    val modelProducer = CartesianChartModelProducer()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                modelProducer.runTransaction {
                    add(RandomCartesianModelGenerator.getRandomCandlestickLayerModelPartial())
                }
                delay(5000L)
            }
        }
    }
}

class Chart3Activity : AppCompatActivity() {
    private lateinit var binding: Chart3Binding

    private val chartViewModel: ChartViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = Chart3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.chartView.modelProducer = chartViewModel.modelProducer

    }
}
