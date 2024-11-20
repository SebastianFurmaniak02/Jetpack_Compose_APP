package com.example.jetpack_compose_app.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.customComposable.AnimatedProgressBar
import com.example.jetpack_compose_app.database.DatabaseHandler

@Composable
fun StatisticsScreen() {

    var animationFinished by remember { mutableStateOf(false) }
    var animationStarted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val columnWeight1 = 0.16f
    val columnWeight2 = 0.2f

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistics",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 25.sp
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if(!animationStarted && !animationFinished) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        animationStarted = true
                    }
                ) {
                    Text(
                        text = "Download data",
                        fontSize = 25.sp
                    )
                }
            }
            else if (animationStarted && !animationFinished) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AnimatedProgressBar(
                        percentage = 1f,
                        number = 100,
                        animDuration = 5000,
                        onAnimationEnd = {
                            animationFinished = true
                        },
                        fontColor = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            else {
                val participants = DatabaseHandler(context).getAllParticipants()
                val genders = listOf("Male", "Female")
                val skillLevels = listOf("Beginner", "Novice", "Intermediate", "Proficient", "Advanced")

                Column (
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Male total: ",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = participants.count {
                                        it.gender == genders[0]
                                    }.toString(),
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Female total: ",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = participants.count {
                                        it.gender == genders[1]
                                    }.toString(),
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Students total: ",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = participants.count {
                                        it.studentStatus == 1
                                    }.toString(),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                            .padding(15.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "",
                                    modifier = Modifier
                                        .weight(columnWeight2),
                                )
                                for (skillLevel in skillLevels) {
                                    Text(
                                        text = "${skillLevel.take(3)}.",
                                        modifier = Modifier
                                            .border(
                                                0.5.dp,
                                                Color.Black,
                                                RectangleShape
                                            )
                                            .weight(columnWeight1),
                                    )
                                }
                            }
                            for (gender in genders) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = gender,
                                        modifier = Modifier
                                            .border(
                                                0.5.dp,
                                                Color.Black,
                                                RectangleShape
                                            )
                                            .weight(columnWeight2),
                                    )
                                    for (skillLevel in skillLevels) {
                                        val count = participants.count {
                                            it.gender == gender && it.skillLevel == skillLevel
                                        }
                                        Text(
                                            text = count.toString(),
                                            modifier = Modifier
                                                .border(
                                                    0.5.dp,
                                                    Color.Black,
                                                    RectangleShape
                                                )
                                                .weight(columnWeight1),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

