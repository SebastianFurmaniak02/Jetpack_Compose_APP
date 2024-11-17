package com.example.jetpack_compose_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose_app.fragments.*
import com.example.jetpack_compose_app.fragments.fragmentDatabase.DatabaseScreen
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme



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
                    ),
                    BottomNavigationItem(
                        title = "Form",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_action_form),
                    ),
                    BottomNavigationItem(
                        title = "Database",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_action_database),
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
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.title
                                            )
                                        }
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
                        composable("Form") { FormScreen() }
                        composable("Database") { DatabaseScreen() }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    CalculatorScreen()
}

