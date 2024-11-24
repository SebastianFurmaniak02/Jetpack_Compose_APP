package com.example.jetpack_compose_app.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.collection.emptyLongSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_compose_app.R
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.database.ParticipantDB
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme
import com.example.jetpack_compose_app.viewModel.DialogInfo
import com.example.jetpack_compose_app.viewModel.FormScreenInfo
import com.example.jetpack_compose_app.viewModel.FormViewModel
import org.jetbrains.annotations.Nullable

class FormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_Compose_APPTheme(dynamicColor = false) {
                val formViewModel: FormViewModel = viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormScreen(formViewModel)
                }
            }
        }
    }
}

@Composable
fun FormScreen(formViewModel: FormViewModel) {
    val context = LocalContext.current
    val formScreenInfo by remember(formViewModel) {
        formViewModel.formScreenInfo
    }.collectAsState()

    val radioButtonGroupOptions = listOf("Male", "Female")
    val paddingVertical = 5.dp
    val paddingHorizontal = 10.dp
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .padding(5.dp)
        ) {
            Text(
                text = "Application form",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 25.sp
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    if (context is Activity)
                        context.finish()
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon (
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_backspace_24),
                    modifier = Modifier
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }
        }

        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = paddingVertical),
                    value = formScreenInfo.firstName,
                    onValueChange = { formViewModel.updateFirstName(it) },
                    singleLine = true,
                    label = {
                        Text("First name")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingVertical),
                    value = formScreenInfo.lastName,
                    singleLine = true,
                    onValueChange = { formViewModel.updateLastName(it)},
                    label = {
                        Text("Last name")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingVertical),
                    value = formScreenInfo.lastName,
                    singleLine = true,
                    onValueChange = { formViewModel.updateLastName(it)},
                    label = {
                        Text("Last name")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingHorizontal)
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Gender", textAlign = TextAlign.Start, fontSize = 20.sp)
                    radioButtonGroupOptions.forEachIndexed { index, label ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (formScreenInfo.selectedIndex == index),
                                onClick = {
                                    formViewModel.updateGender(index)
                                },
                            )
                            Text(text = label, fontSize = 15.sp)
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                        .padding(horizontal = paddingHorizontal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Student status", fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = formScreenInfo.studentStatus,
                        onCheckedChange = {
                            formViewModel.toggleStudentStatus(it)
                        }
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = paddingHorizontal,
                            end = paddingHorizontal,
                            bottom = paddingVertical
                        )
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(15.dp))
                        .padding(
                            start = paddingHorizontal,
                            end = paddingHorizontal,
                            bottom = paddingVertical
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Skill level", fontSize = 20.sp)
                    Slider(
                        modifier = Modifier.padding(horizontal = paddingVertical),
                        value = formScreenInfo.skillLevel.toFloat(),
                        onValueChange = {
                            formViewModel.updateSkillLevel(it.toInt())
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        steps = 3,
                        valueRange = 0f..4f
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingHorizontal),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Beginner",
                            fontSize = 15.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Advanced",
                            fontSize = 15.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                }
            }
            item {
                Row(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(paddingHorizontal),
                        onClick = {
                            formViewModel.updateDialog(
                                if  (formScreenInfo.firstName.isEmpty() || formScreenInfo.lastName.isEmpty() || formScreenInfo.email.isEmpty())
                                    DialogInfo(true, 0)
                                else if (!Patterns.EMAIL_ADDRESS.matcher(formScreenInfo.email.trim()).matches())
                                    DialogInfo(true, 1)
                                else {
                                    DialogInfo(true, 2)
                                }
                            )
                        }
                    ) {
                        Text("Send", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        if (formScreenInfo.dialogInfo.isDialog) {
            AlertDialog(
                onDismissRequest = {
                    if (formScreenInfo.dialogInfo.dialogNumber == 2) {
                        val participant = ParticipantDB(
                            id = null,
                            firstName = formScreenInfo.firstName,
                            lastName = formScreenInfo.lastName,
                            email = formScreenInfo.email,
                            gender = radioButtonGroupOptions[formScreenInfo.selectedIndex],
                            studentStatus = when {
                                formScreenInfo.studentStatus -> 1
                                else -> 0
                            },
                            skillLevel = when (formScreenInfo.skillLevel) {
                                0 -> "Beginner"
                                1 -> "Novice"
                                2 -> "Intermediate"
                                3 -> "Proficient"
                                else -> "Advanced" }
                        )
                        DatabaseHandler(context).insertData(participant)
                        if (context is Activity)
                            context.finish()
                    }
                    formViewModel.updateDialog(DialogInfo(false,0))
                },
                text = {
                    Text(
                        when (formScreenInfo.dialogInfo.dialogNumber) {
                            0 -> "Some fields are empty."
                            1 -> "invalid email address."
                            else -> "The form has been sent."
                        }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (formScreenInfo.dialogInfo.dialogNumber == 2) {
                                val participant = ParticipantDB(
                                    id = null,
                                    firstName = formScreenInfo.firstName,
                                    lastName = formScreenInfo.lastName,
                                    email = formScreenInfo.email,
                                    gender = radioButtonGroupOptions[formScreenInfo.selectedIndex],
                                    studentStatus = when {
                                        formScreenInfo.studentStatus -> 1
                                        else -> 0
                                    },
                                    skillLevel = when (formScreenInfo.skillLevel) {
                                        0 -> "Beginner"
                                        1 -> "Novice"
                                        2 -> "Intermediate"
                                        3 -> "Proficient"
                                        else -> "Advanced" }
                                )
                                DatabaseHandler(context).insertData(participant)
                                if (context is Activity)
                                    context.finish()
                            }
                            formViewModel.updateDialog(DialogInfo(false,0))
                        }) {
                        Text("OK")
                    }
                },
            )
        }
    }
}