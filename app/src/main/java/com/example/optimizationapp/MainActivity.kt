package com.example.optimizationapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.math.pow
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException
import android.widget.TextView
import net.objecthunter.exp4j.Expression


class MainActivity : AppCompatActivity() {

    private lateinit var functionInput: EditText
    private lateinit var VarInput: EditText
    private lateinit var LRInput: EditText
    private lateinit var IterInput: EditText
    private lateinit var optimizeButton: Button
    private lateinit var zeroPoints: TextView
    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        functionInput = findViewById(R.id.functionInput)
        VarInput = findViewById(R.id.VarInput)
        LRInput = findViewById(R.id.LRInput)
        IterInput = findViewById(R.id.IterInput)
        optimizeButton = findViewById(R.id.optimizeButton)
        zeroPoints = findViewById(R.id.zeroPoints)
        chart = findViewById(R.id.chart)

        optimizeButton.setOnClickListener {
            val functionStr = functionInput.text.toString()

            val VarList = VarInput.text.toString().split(",").toTypedArray()

            val LRstr = LRInput.text.toString()
            val LR = if (LRstr.isNullOrBlank()) 0.1f else LRstr.toFloat()

            val Iterstr = IterInput.text.toString()
            val Iter = if (Iterstr.isNullOrBlank()) 100 else Iterstr.toInt()

            val optimizedPoints = optimizeFunction(functionStr, VarList, LR, Iter)

            displayChart(optimizedPoints)
            displayZeros(optimizedPoints)
        }
    }

    private fun grad(expr: Expression, var_point: Pair<String, Float>, delta: Float): Double{
        val var_p = var_point.second.toDouble()

        expr.setVariable(var_point.first, var_p)
        val value = expr.evaluate()

        val grad = (expr.setVariable(var_point.first, var_p + delta).evaluate() - value) / delta

        return grad
    }


    private fun optimizeFunction(functionStr: String, VarList: Array<String>, LearningRate: Float, Iter: Int): List<List<Pair<String, Float>>> {
        val points = mutableListOf<List<Pair<String, Float>>>()
        val learningRate = LearningRate
        val delta = 1e-6
        val epsilon = 1e-6
        val iter = Iter

        var PointsList = arrayListOf<Pair<String, Float>>()

        for (var_point in VarList){
            val pair = Pair(var_point, 1.0f)
            PointsList.add(pair)
        }

        try {
            val expression = ExpressionBuilder(functionStr)
                .variables(VarList.toSet())
                .build()

            for (point in PointsList){
                expression.setVariable(point.first, point.second.toDouble())
            }

            for (i in 0..iter) {
                val GradList = arrayListOf<Pair<String, Float>>()

                for (point in PointsList){
                    val grad = grad(expression, point, delta.toFloat())
                    val p = point.second - learningRate * grad
                    val pair = Pair(point.first, p.toFloat())

                    GradList.add(pair)
                    PointsList = GradList
                }

                points.add(GradList)

                var mean = 0.0f
                for (grad in GradList){
                    mean += grad.second.pow(2)
                }
                mean = mean / GradList.size
                if (mean < epsilon) break
            }
        } catch (e: UnknownFunctionOrVariableException) {
            e.printStackTrace()
        }

        return points
    }

    private fun displayChart(points: List<List<Pair<String, Float>>>) {
        val MeanArray = mutableListOf<Float>()

        for (point in points){
            var mean = 0.0f
            for (grad in point){
                mean += grad.second.pow(2)
            }
            mean = mean / point.size

            MeanArray.add(mean)
        }

        val entries = MeanArray.mapIndexed { index, point ->
            Entry(index.toFloat(), point)
        }

        val dataSet = LineDataSet(entries, "Optimization Path")
        dataSet.lineWidth = 2.5f
        dataSet.circleRadius = 4.5f
        dataSet.color = resources.getColor(R.color.purple_500, null)
        dataSet.setCircleColor(resources.getColor(R.color.purple_500, null))
        dataSet.valueTextSize = 10f

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()
    }

    private fun displayZeros(points: List<List<Pair<String, Float>>>) {
        if (points.isNotEmpty()) {
            val Points = points.last()
            var text = "Zeros:"

            for (point in Points){
                val variable = point.first
                val p = point.second
                text += " $variable = %.5f".format(p)
            }

            zeroPoints.text = text
        } else {
            zeroPoints.text = "Zeros: Not found"
        }
    }
}
