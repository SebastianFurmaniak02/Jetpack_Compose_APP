package com.example.jetpack_compose_app.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.R

@Composable
fun SensorScreen() {
    val image = ImageBitmap.imageResource(R.drawable.ic_action_sun)
    var imageSun by remember { mutableStateOf(image) }
    var progress by remember { mutableFloatStateOf(0.0F) }
    var textProgress by remember { mutableStateOf("0%") }
    val context = LocalContext.current
    val pixels = context.resources.displayMetrics.widthPixels
    val density = context.resources.displayMetrics.density
    val width = (pixels/density).dp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sensor",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 25.sp
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .width(width / 2)
                        .height(width / 2),
                    bitmap = imageSun,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Output:",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = textProgress,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                }
            }
            DisposableEffect(Unit) {
                val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
                val sensorListener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent?) {
                        val sensorOutput = event!!.values[0]
                        val brightness = if (sensorOutput>= 10000) 110f else (sensorOutput* 160/10000) - 50
                        val percentageOutput = ((brightness + 50) * 100 / 160)
                        progress = (percentageOutput/100)
                        textProgress = "${percentageOutput.toInt()}%"
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
