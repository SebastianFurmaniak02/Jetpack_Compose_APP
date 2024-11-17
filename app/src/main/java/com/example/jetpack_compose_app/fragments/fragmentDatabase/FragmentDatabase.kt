package com.example.jetpack_compose_app.fragments.fragmentDatabase

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.ui.theme.Purple40


@Composable
fun DatabaseScreen() {
    val context = LocalContext.current
    val participantList = DatabaseHandler(context).getAllParticipants()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Participants: ",
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (participantList.isNotEmpty()) {
            LazyColumn(
                Modifier.padding(10.dp)
            ) {
                items(participantList) { participant ->
                    Column(
                        modifier = Modifier
                            .background(Purple40)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                val intent = Intent(context, ParticipantDetails::class.java).apply {
                                    putExtra("PARTICIPANT_ID", participant.id.toString())
                                }
                                context.startActivity(intent)
                            }
                    ) {
                        Text(participant.firstName + " " + participant.lastName)
                        Text(participant.email)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}