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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.jetpack_compose_app.R

@Composable
fun SensorScreen() {
    val image = ImageBitmap.imageResource(R.drawable.ic_action_sun)
    var imageSun by remember { mutableStateOf(image) }
    var progress by remember { mutableFloatStateOf(0.0F) }
    var textProgress by remember { mutableStateOf("0%") }
    val context = LocalContext.current
    var isLandscape = false
    if(LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        isLandscape = true
    }
    var size: Dp
    val density = context.resources.displayMetrics.density
    size = if (isLandscape) {
        val pixels = LocalContext.current.resources.displayMetrics.heightPixels
        (pixels/density).dp / 3
    } else {
        val pixels = LocalContext.current.resources.displayMetrics.widthPixels
        (pixels/density).dp / 2
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
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

        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (image, outputInfo) = createRefs()

            Image(
                modifier = Modifier
                    .size(size)
                    .constrainAs(image) {
                        if (isLandscape) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(outputInfo.start)
                        } else {
                            bottom.linkTo(outputInfo.top, 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    },
                bitmap = imageSun,
                contentDescription = ""
            )
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                    .padding(20.dp)
                    .constrainAs(outputInfo) {

                        if (isLandscape) {
                            top.linkTo(parent.top)
                            start.linkTo(image.end)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        } else {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text(
                    text = "Output:",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                LinearProgressIndicator(
                    progress = progress,
                )
                Text(
                    text = textProgress,
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

private fun setBrightness(src: Bitmap, value: Int): Bitmap? {
    val width = src.width
    val height = src.height
    val bmOut = src.config?.let { Bitmap.createBitmap(width, height, it) } ?: return null
    var A: Int
    var R: Int
    var G: Int
    var B: Int
    var pixel: Int

    for (x in 0 until width) {
        for (y in 0 until height) {

            pixel = src.getPixel(x, y)
            A = Color.alpha(pixel)
            R = Color.red(pixel)
            G = Color.green(pixel)
            B = Color.blue(pixel)

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

            bmOut.setPixel(x, y, Color.argb(A, R, G, B))
        }
    }
    return bmOut
}
