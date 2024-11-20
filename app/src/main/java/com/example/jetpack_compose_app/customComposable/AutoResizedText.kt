package com.example.jetpack_compose_app.customComposable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    modifier: Modifier = Modifier,
    color: Color = style.color,
    textAlign: TextAlign? = null,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize
) {
    var resizedTextStyle by remember { mutableStateOf(style.copy(fontSize = fontSize)) }
    var shouldDraw by remember { mutableStateOf(false) }

    Text(
        style = resizedTextStyle,
        text = text,
        color = color,
        textAlign = textAlign,
        modifier = modifier.drawWithContent {
            if(shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        onTextLayout = { result ->
            if(result.didOverflowWidth) {
                if(style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = fontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize*0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}