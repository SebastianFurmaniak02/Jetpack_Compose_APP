package com.example.jetpack_compose_app.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.R
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.database.ParticipantDB
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme

class ParticipantDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseHandler(this)
        val participantID = intent.getStringExtra("PARTICIPANT_ID")
        val participant = participantID?.let { db.getRecord(it) }
        setContent {
            Jetpack_Compose_APPTheme(dynamicColor = false) {

                if (participant != null) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ScreenParticipantDetails(participant)
                    }
                } else {
                    Toast.makeText(this, "Participant doesn't exist.", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }
    }
}


@Composable
fun ScreenParticipantDetails(participant: ParticipantDB) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false)  }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .background(MaterialTheme.colorScheme.tertiary, RectangleShape)
                .padding(5.dp)
        ) {
            Text(
                text = "Participant details",
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
            Button(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    openDialog.value = true
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon (
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
                    modifier = Modifier
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    text = {
                        Text("Are you sure you want to delete this participant?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                DatabaseHandler(context).deleteParticipant(participant.id)
                                if (context is Activity)
                                    context.finish()
                            }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("No")
                        }
                    }
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
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
                        text = "Name:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "${participant.firstName} ${participant.lastName}",
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Email:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = participant.email,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Gender:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = participant.gender,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Student:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = if (participant.studentStatus == 1) "Yes" else "No",
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Skill Level:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = participant.skillLevel,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}