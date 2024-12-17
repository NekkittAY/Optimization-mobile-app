# Optimization Mobile App

A mobile application for mathematical function optimization using gradient descent method. The app allows users to input custom functions and visualize the optimization process through an interactive chart.

## Features

- Custom function input support
- Multiple variable optimization
- Adjustable learning rate and iteration count
- Real-time visualization of optimization progress
- Zero points display for optimized functions
- Interactive line chart showing convergence

## Technologies Used

- Kotlin
- Android Jetpack Compose
- MPAndroidChart for data visualization
- exp4j for mathematical expression parsing
- Commons Math3 library

## Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or higher
- Android SDK with minimum API level 24

## Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/Optimization-mobile-app.git
```
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the application on an emulator or physical device

## Project Structure

app/
├── build.gradle.kts           # Project dependencies and configuration
├── src/
│   └── main/
│       ├── java/
│       │   └── com.example.optimizationapp/
│       │       ├── MainActivity.kt        # Main application logic
│       │       ├── Function.kt            # Function definition class (unused now)
│       │       └── Optimization.kt        # Optimization algorithms (unused now)
│       └── res/                          # Resource files
└── AndroidManifest.xml        # App configuration

## Usage

1. **Enter the function:** Type the function you want to minimize into the "Function" input field. Use standard mathematical notation (e.g., `x^2 + y^2`).
2. **Specify Variables:** List the variables used in your function, separated by commas (e.g., `x,y`).
3. **Set Learning Rate:** Enter the desired learning rate for the Gradient Descent algorithm. A smaller value will result in slower but potentially more accurate convergence.  The default value is 0.1.
4. **Set Iterations:** Enter the maximum number of iterations for the algorithm. A larger value can increase accuracy but also computation time.  The default value is 100.
5. **Tap "Optimize":**  The app will calculate the optimization path and display the results on the chart and in the "Zeros" text view.

## Example

To minimize the function  `x^2 + y^2`, you would:

1. Enter `x^2 + y^2` in the "Function" field.
2. Enter `x,y` in the "Variables" field.
3. (Optional) Adjust the learning rate and iterations.
4. Tap "Optimize".

The app will then display the optimization path and the calculated minimum point, which should be close to (0, 0).

## Future Improvements

* Implement additional optimization algorithms (e.g., Newton's method, Conjugate Gradient).
* Allow users to define constraints for the variables.
* Improve the visualization of the optimization process.
* Add input validation and error handling.
* Add support for more complex mathematical functions.
