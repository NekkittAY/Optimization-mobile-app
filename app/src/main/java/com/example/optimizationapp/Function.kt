package com.example.optimizationapp

data class Function(val name: String, val expression: String) {
    fun evaluate(x: Double, y: Double): Double {
        return when (name) {
            "f1" -> x * x + y * y
            "f2" -> x * x - y * y
            else -> 0.0
        }
    }
}