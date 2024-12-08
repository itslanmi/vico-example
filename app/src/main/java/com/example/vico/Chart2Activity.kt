package com.example.vico

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.vico.databinding.Chart2Binding
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalBox
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.util.Locale
import kotlin.random.Random


private const val HORIZONTAL_BOX_COLOR = -1448529
private const val HORIZONTAL_BOX_ALPHA = 0.36f
private const val HORIZONTAL_BOX_LABEL_HORIZONTAL_PADDING_DP = 8f
private const val HORIZONTAL_BOX_LABEL_VERTICAL_PADDING_DP = 2f
private const val HORIZONTAL_BOX_LABEL_MARGIN_DP = 4f

private val horizontalBoxY = 10.0..14.0
private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val bottomAxisValueFormatter = CartesianValueFormatter { _, x, _ ->
    daysOfWeek[x.toInt() % daysOfWeek.size]
}

class Chart2Activity : AppCompatActivity() {
    private lateinit var binding: Chart2Binding
    private val modelProducer = CartesianChartModelProducer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Chart2Binding.inflate(layoutInflater).apply {
            with(chartView) {
                chart =
                    chart?.copy(
                        bottomAxis =
                        (chart?.bottomAxis as HorizontalAxis).copy(valueFormatter = bottomAxisValueFormatter),
                        decorations = listOf(getViewHorizontalBox()),
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


    private fun getViewHorizontalBox(): HorizontalBox {
        val color = Color(HORIZONTAL_BOX_COLOR)
        return HorizontalBox(
            y = { horizontalBoxY },
            box = ShapeComponent(Fill(color.copy(HORIZONTAL_BOX_ALPHA).toArgb())),
            labelComponent =
            TextComponent(
                margins = Dimensions(HORIZONTAL_BOX_LABEL_MARGIN_DP),
                padding =
                Dimensions(
                    HORIZONTAL_BOX_LABEL_HORIZONTAL_PADDING_DP,
                    HORIZONTAL_BOX_LABEL_VERTICAL_PADDING_DP,
                ),
                background = ShapeComponent(Fill(color.toArgb()), Shape.Rectangle),
            ),
        )
    }


}