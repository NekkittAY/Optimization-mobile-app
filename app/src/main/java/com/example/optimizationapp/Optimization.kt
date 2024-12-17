package com.example.optimizationapp

class Optimization {
    companion object {
        fun gradientDescent(function: Function, x0: Double, y0: Double, learningRate: Double, iterations: Int): Pair<Double, Double> {
            var x = x0
            var y = y0
            for (i in 0 until iterations) {
                val gradientX = (function.evaluate(x + 0.001, y) - function.evaluate(x - 0.001, y)) / 0.002
                val gradientY = (function.evaluate(x, y + 0.001) - function.evaluate(x, y - 0.001)) / 0.002
                x -= learningRate * gradientX
                y -= learningRate * gradientY
            }
            return Pair(x, y)
        }

        fun newtonMethod(function: Function, x0: Double, y0: Double, iterations: Int): Pair<Double, Double> {
            var x = x0
            var y = y0
            for (i in 0 until iterations) {
                val gradientX = (function.evaluate(x + 0.001, y) - function.evaluate(x - 0.001, y)) / 0.002
                val gradientY = (function.evaluate(x, y + 0.001) - function.evaluate(x, y - 0.001)) / 0.002
                val hessianXX = (function.evaluate(x + 0.001, y) - 2 * function.evaluate(x, y) + function.evaluate(x - 0.001, y)) / 0.001
                val hessianYY = (function.evaluate(x, y + 0.001) - 2 * function.evaluate(x, y) + function.evaluate(x, y - 0.001)) / 0.001
                val hessianXY = (function.evaluate(x + 0.001, y + 0.001) - function.evaluate(x + 0.001, y - 0.001) - function.evaluate(x - 0.001, y + 0.001) + function.evaluate(x - 0.001, y - 0.001)) / 0.004
                val determinant = hessianXX * hessianYY - hessianXY * hessianXY
                val invHessianXX = hessianYY / determinant
                val invHessianYY = hessianXX / determinant
                val invHessianXY = -hessianXY / determinant
                x -= invHessianXX * gradientX + invHessianXY * gradientY
                y -= invHessianXY * gradientX + invHessianYY * gradientY
            }
            return Pair(x, y)
        }
    }
}