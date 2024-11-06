package com.example.jetpack_compose_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose_app.fragments.CalculatorScreen
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme
import com.example.jetpack_compose_app.ui.theme.Purple40
import kotlin.math.sqrt


data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_Compose_APPTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Calculator",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_action_calculator),
                    ),
                    BottomNavigationItem(
                        title = "Sensor",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_action_sensor),
                    )
                )

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.title)
                                        },
                                        alwaysShowLabel = false,
                                        label = { Text(text = item.title) },
                                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) }
                                    )
                                }
                            }
                        }
                    ) {}

                    NavHost(
                        navController = navController,
                        startDestination = "Calculator",
                    ) {
                        composable("Calculator") { CalculatorScreen() }
                        composable("Sensor") { SensorScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun SensorScreen() {
    Text("Sensor Screen")
}

@Preview
@Composable
fun SimpleComposablePreview() {
    CalculatorScreen()
}

