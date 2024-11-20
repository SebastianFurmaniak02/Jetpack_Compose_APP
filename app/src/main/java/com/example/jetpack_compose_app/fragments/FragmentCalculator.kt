package com.example.jetpack_compose_app.fragments


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.R
import com.example.jetpack_compose_app.customComposable.AutoResizedText
import com.example.jetpack_compose_app.ui.theme.Cyan
import kotlin.math.sqrt

data class CalculatorScreenInfo(
    var screen: String,
    var isDot: Boolean,
    var isLastSymbol: Boolean,
    var isResult: Boolean
)

@Composable
fun CalculatorScreen() {
    var calculatorScreenInfo by remember { mutableStateOf(CalculatorScreenInfo(
        screen = "0",
        isDot = false,
        isLastSymbol = false,
        isResult = true
    )) }
    val pixels = LocalContext.current.resources.displayMetrics.widthPixels
    val density = LocalContext.current.resources.displayMetrics.density
    val buttonWidth = ((pixels/density - 8)/4).dp

    Column(
        modifier = Modifier.fillMaxSize(),
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
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                    .padding(5.dp)
                    .height(buttonWidth)
                    .align(Alignment.Bottom),
                textAlign = TextAlign.End,
                fontSize = 80.sp
            )
        }
        Column (modifier = Modifier.fillMaxHeight()) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = square(calculatorScreenInfo) }) {
                    Text(AnnotatedString("x\u00B2"), fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = squareRoot(calculatorScreenInfo) }) {
                    Text(stringResource(id = R.string.square_root_symbol), fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = clearScreen(calculatorScreenInfo) }) {
                    Text("C", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = setSymbolOnScreen("/", calculatorScreenInfo) }) {
                    Text(stringResource(id = R.string.division), fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("1", calculatorScreenInfo) }) {
                    Text("1", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("2", calculatorScreenInfo) }) {
                    Text("2", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("3", calculatorScreenInfo) }) {
                    Text("3", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = setSymbolOnScreen("*", calculatorScreenInfo) }) {
                    Text(stringResource(id = R.string.multiplication), fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("4", calculatorScreenInfo) }) {
                    Text("4", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("5", calculatorScreenInfo) }) {
                    Text("5", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("6", calculatorScreenInfo) }) {
                    Text("6", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = setSymbolOnScreen("-", calculatorScreenInfo) }) {
                    Text("-", fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("7", calculatorScreenInfo) }) {
                    Text("7", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("8", calculatorScreenInfo) }) {
                    Text("8", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("9", calculatorScreenInfo) }) {
                    Text("9", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = setSymbolOnScreen("+", calculatorScreenInfo) }) {
                    Text("+", fontSize=30.sp)
                }
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    onClick = { calculatorScreenInfo = setNumberOnScreen("0", calculatorScreenInfo) }) {
                    Text("0", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = changeSign(calculatorScreenInfo) }) {
                    Text("+/-", fontSize=25.sp, maxLines = 1)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = setSymbolOnScreen(".", calculatorScreenInfo) }) {
                    Text(".", fontSize=30.sp)
                }
                Button(
                    modifier = Modifier
                        .size(buttonWidth, buttonWidth)
                        .padding(2.dp),
                    shape = RoundedCornerShape(15.dp),
                    onClick = { calculatorScreenInfo = calculateResult(calculatorScreenInfo) }) {
                    Text("=", fontSize=30.sp)
                }
            }
        }
    }
}

private fun setNumberOnScreen(number: String, calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    if (newInfo.screen == "0") {
        newInfo.screen = number
    } else {
        newInfo.screen += number
    }
    newInfo.isLastSymbol = false
    return newInfo
}

private fun setSymbolOnScreen(symbol: String, calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    if (symbol == "." && !calculatorScreenInfo.isDot) {
        newInfo.isDot = true
        newInfo.isLastSymbol = false
        newInfo.screen += symbol
    } else if (symbol != "." && !newInfo.isLastSymbol) {
        newInfo.isResult = false
        newInfo.isDot = false
        newInfo.isLastSymbol = true
        newInfo.screen += symbol
    }
    return newInfo
}

private fun changeSign(calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    if (newInfo.isResult) {
        var result = newInfo.screen.toDouble()
        result *= -1
        if (result % 1.0 == 0.0) {
            newInfo.screen = result.toInt().toString()
        } else {
            newInfo.screen = result.toString()
            newInfo.isDot = true
        }
    }
    return newInfo
}

private fun clearScreen(calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    newInfo.screen = "0"
    newInfo.isResult = true
    newInfo.isDot = false
    newInfo.isLastSymbol = false
    return newInfo
}

private fun squareRoot(calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    var result = newInfo.screen.toDouble()
    if (newInfo.isResult && result > 0) {
        result = sqrt(result)
        if (result % 1.0 == 0.0) {
            newInfo.screen = result.toInt().toString()
        } else {
            newInfo.screen = result.toString()
            newInfo.isDot = true
        }
    }
    return newInfo
}

private fun square(calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {
    val newInfo = calculatorScreenInfo.copy()
    if (newInfo.isResult) {
        var result = newInfo.screen.toDouble()
        result *= result
        if (result % 1.0 == 0.0) {
            newInfo.screen = result.toInt().toString()
        } else {
            newInfo.screen = result.toString()
            newInfo.isDot = true
        }
    }
    return newInfo
}

private fun calculateResult(calculatorScreenInfo: CalculatorScreenInfo) : CalculatorScreenInfo {

    if (calculatorScreenInfo.isLastSymbol || calculatorScreenInfo.isResult) return calculatorScreenInfo

    var screen = calculatorScreenInfo.screen
        .replace("--","-~")
        .replace("+-","+~")
        .replace("/-","/~")
        .replace("*-","*~")
    if (screen.startsWith("-")) {
        screen = screen.removePrefix("-")
        screen = "~$screen"
    }
    val numbers = screen.split("+", "-", "*", "/").filter { it.isNotEmpty() }.toMutableList()
    val operators = screen.split(Regex("[~0-9.]+")).filter { it.isNotEmpty() }.toMutableList()
    var result = 0.0
    var index = 0
    for (i in numbers.indices) {
        numbers[i] = numbers[i].replace("~", "-")
    }

    while (index != -1) {
        index = operators.indexOfFirst { it in setOf("*", "/") }
        if (index != -1) {
            val number1 = numbers[index].toDouble()
            val number2 = numbers[index + 1].toDouble()
            result = if (operators[index] == "*") {
                number1 * number2
            } else {
                number1 / number2
            }
            operators.removeAt(index)
            numbers.removeAt(index)
            numbers[index] = result.toString()
        }
    }
    index = 0
    while (index != -1) {
        index = operators.indexOfFirst { it in setOf("+", "-") }
        if (index != -1) {
            val number1 = numbers[index].toDouble()
            val number2 = numbers[index + 1].toDouble()
            result = if (operators[index] == "+") {
                number1 + number2
            } else {
                number1 - number2
            }
            operators.removeAt(index)
            numbers.removeAt(index)
            numbers[index] = result.toString()
        }
    }

    val newInfo = clearScreen(calculatorScreenInfo)
    if (result % 1.0 == 0.0) {
        newInfo.screen = result.toInt().toString()
    } else {
        newInfo.screen = result.toString()
        newInfo.isDot = true
    }

    return newInfo
}