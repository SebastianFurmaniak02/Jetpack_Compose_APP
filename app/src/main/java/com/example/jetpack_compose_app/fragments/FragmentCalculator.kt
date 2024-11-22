package com.example.jetpack_compose_app.fragments


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.R
import com.example.jetpack_compose_app.customComposable.AutoResizedText
import com.example.jetpack_compose_app.ui.theme.Cyan
import com.example.jetpack_compose_app.viewModel.CalculatorViewModel
import kotlin.math.sqrt

@Composable
fun CalculatorScreen(calculatorViewModel: CalculatorViewModel) {
    val calculatorScreenInfo by remember(calculatorViewModel) {
        calculatorViewModel.calculatorScreenInfo
    }.collectAsState()
    var isLandscape = false


    if(LocalConfiguration.current.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        isLandscape = true
    }

    val pixels =
        if (isLandscape) LocalContext.current.resources.displayMetrics.heightPixels
        else LocalContext.current.resources.displayMetrics.widthPixels

    val density = LocalContext.current.resources.displayMetrics.density
    val buttonSize = if (isLandscape) ((pixels/density - 8)/4).dp - 30.dp
            else ((pixels/density)/4).dp - 4.dp

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
            ) {
            AutoResizedText(
                text = calculatorScreenInfo.screen.take(20),
                modifier = Modifier
                    .fillMaxWidth().height(100.dp)
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                    .padding(5.dp)
                    .align(Alignment.Bottom),
                textAlign = TextAlign.End,
                fontSize = 80.sp
            )
        }
        if(!isLandscape) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.square() }) {
                    AutoResizedText(AnnotatedString("x\u00B2").toString(), fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.squareRoot() }) {
                    AutoResizedText(stringResource(id = R.string.square_root_symbol), fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.clearScreen() }) {
                    AutoResizedText("C", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.setSymbolOnScreen("/") }) {
                    AutoResizedText(stringResource(id = R.string.division), fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("1") }) {
                    AutoResizedText("1", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("2") }) {
                    AutoResizedText("2", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("3") }) {
                    AutoResizedText("3", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.setSymbolOnScreen("*") }) {
                    AutoResizedText(stringResource(id = R.string.multiplication), fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("4") }) {
                    AutoResizedText("4", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("5") }) {
                    AutoResizedText("5", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("6") }) {
                    AutoResizedText("6", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.setSymbolOnScreen("-") }) {
                    AutoResizedText("-", fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("7") }) {
                    AutoResizedText("7", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("8") }) {
                    AutoResizedText("8", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("9") }) {
                    AutoResizedText("9", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.setSymbolOnScreen("+") }) {
                    AutoResizedText("+", fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorViewModel.setNumberOnScreen("0") }) {
                    AutoResizedText("0", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.changeSign() }) {
                    AutoResizedText("+/-", fontSize=25.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.setSymbolOnScreen(".") }) {
                    AutoResizedText(".", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonSize, buttonSize)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorViewModel.calculateResult() }) {
                    AutoResizedText("=", fontSize=30.sp)
                }
            }
        } else {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("1") }) {
                        AutoResizedText("1", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("4") }) {
                        AutoResizedText("4", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("7") }) {
                        AutoResizedText("7", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("2") }) {
                        AutoResizedText("2", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("5") }) {
                        AutoResizedText("5", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("8") }) {
                        AutoResizedText("8", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("3") }) {
                        AutoResizedText("3", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("6") }) {
                        AutoResizedText("6", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("9") }) {
                        AutoResizedText("9", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.setSymbolOnScreen("+") }) {
                        AutoResizedText("+", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.setSymbolOnScreen("-") }) {
                        AutoResizedText("-", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        onClick = { calculatorViewModel.setNumberOnScreen("0") }) {
                        AutoResizedText("0", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.setSymbolOnScreen("*") }) {
                        AutoResizedText(stringResource(id = R.string.multiplication), fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.setSymbolOnScreen("/") }) {
                        AutoResizedText(stringResource(id = R.string.division), fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.setSymbolOnScreen(".") }) {
                        AutoResizedText(".", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.square() }) {
                        AutoResizedText(AnnotatedString("x\u00B2").toString(), fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.squareRoot() }) {
                        AutoResizedText(stringResource(id = R.string.square_root_symbol), fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.changeSign() }) {
                        AutoResizedText("+/-", fontSize=30.sp)
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(0.33f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.clearScreen() }) {
                        AutoResizedText("C", fontSize=30.sp)
                    }
                    Button(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .weight(0.67f),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { calculatorViewModel.calculateResult() }) {
                        AutoResizedText("=", fontSize=30.sp)
                    }

                }
            }
        }
    }
}