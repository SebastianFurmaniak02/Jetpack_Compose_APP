package com.example.jetpack_compose_app.fragments.fragmentDatabase

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.database.ParticipantDB
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme

class ParticipantDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseHandler(this)
        val participantID = intent.getStringExtra("PARTICIPANT_ID")
        val participant = participantID?.let { db.getRecord(it) }

        setContent {
            Jetpack_Compose_APPTheme {
                if (participant != null) {
                    ScreenParticipantDetails(this, participant)
                } else {
                    Toast.makeText(this, "Participant doesn't exist.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}

@Composable
fun ScreenParticipantDetails(context: Context, participant: ParticipantDB) {
    //val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text("First name: " + participant.firstName)
            Text("Last name: " + participant.lastName)
            Text("Email: " + participant.email)
            Text("Gender: " + participant.gender)
            Text("Student status: " + when {
                participant.studentStatus == 1 -> "Yes"
                else -> "No"
            })
            Text("Skill level: " + participant.skillLevel.toString())
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        DatabaseHandler(context).deleteParticipant(participant.id)
                        if (context is Activity)
                            context.finish()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        ) {
            Text("Delete")
        }
    }
}