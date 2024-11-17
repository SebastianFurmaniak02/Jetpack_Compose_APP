package com.example.jetpack_compose_app.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.example.jetpack_compose_app.R

@Composable
fun SensorScreen() {
    val image = ImageBitmap.imageResource(R.drawable.ic_action_sun)
    var imageSun by remember { mutableStateOf(image) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = imageSun,
            contentDescription = ""
        )
    }
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val brightness = event!!.values[0] * (160/50) - 80
                imageSun = setBrightness(image.asAndroidBitmap(), -brightness.toInt())?.asImageBitmap()
                    ?: imageSun
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }
        sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

private fun setBrightness(src: Bitmap, value: Int): Bitmap? {
    // original image size
    val width = src.width
    val height = src.height
    // create output bitmap
    val bmOut = src.config?.let { Bitmap.createBitmap(width, height, it) } ?: return null
    // color information
    var A: Int
    var R: Int
    var G: Int
    var B: Int
    var pixel: Int

    // scan through all pixels
    for (x in 0 until width) {
        for (y in 0 until height) {
            // get pixel color
            pixel = src.getPixel(x, y)
            A = Color.alpha(pixel)
            R = Color.red(pixel)
            G = Color.green(pixel)
            B = Color.blue(pixel)

            // increase/decrease each channel
            R += value
            if (R > 255) {
                R = 255
            } else if (R < 0) {
                R = 0
            }
            G += value
            if (G > 255) {
                G = 255
            } else if (G < 0) {
                G = 0
            }
            B += value
            if (B > 255) {
                B = 255
            } else if (B < 0) {
                B = 0
            }

            // apply new pixel color to output bitmap
            bmOut.setPixel(x, y, Color.argb(A, R, G, B))
        }
    }

    // return final image
    return bmOut
}
