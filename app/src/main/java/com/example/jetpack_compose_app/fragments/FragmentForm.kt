package com.example.jetpack_compose_app.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.database.ParticipantDB

@Composable
fun FormScreen() {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val radioButtonGroupOptions = listOf("Male", "Female", "Other")
    var studentStatus by remember { mutableStateOf(false) }
    var skillLevel by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Application form for a basketball camp",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 18.sp
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text("First name")
                    }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text("Last name")
            }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Email")
            }
        )
        Text("Gender", textAlign = TextAlign.Start)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            radioButtonGroupOptions.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                   RadioButton(
                       selected = (selectedIndex == index),
                       onClick = {
                           selectedIndex = index
                       },
                   )
                    Text(label)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Student status" ,
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = studentStatus,
                onCheckedChange = {
                    studentStatus = it
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                value = skillLevel.toFloat(),
                onValueChange = {
                    skillLevel = it.toInt()
                },
                steps = 11,
                valueRange = 0f..10f
            )
            //Text(text = skillLevel.toString())
            Row {
                Text(
                    text = "Beginner"
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Advanced"
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val participant = ParticipantDB(
                    id = null,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    gender = radioButtonGroupOptions[selectedIndex],
                    studentStatus = when {
                        studentStatus -> 1
                        else -> 2
                    },
                    skillLevel = skillLevel
                )
                DatabaseHandler(context).insertData(participant)
            }
        ) {
            Text("Send")
        }
    }
}

