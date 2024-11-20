package com.example.jetpack_compose_app.fragments

import android.content.Intent
import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.jetpack_compose_app.R
import com.example.jetpack_compose_app.activities.FormActivity
import com.example.jetpack_compose_app.activities.ParticipantDetailsActivity
import com.example.jetpack_compose_app.database.DatabaseHandler
import com.example.jetpack_compose_app.ui.theme.Jetpack_Compose_APPTheme


@Composable
fun DatabaseScreen() {
    val context = LocalContext.current
    var participantList by remember {
        mutableStateOf(DatabaseHandler(context).getAllParticipants())
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val state by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(state) {
        if (state == Lifecycle.State.RESUMED) {
            participantList = DatabaseHandler(context).getAllParticipants()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .padding(5.dp)
            ) {
            Text(
                text = "Participants database",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 25.sp
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    val intent = Intent(context, FormActivity::class.java)
                    context.startActivity(intent)
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon (
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_person_add_24),
                    modifier = Modifier
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (participantList.isNotEmpty()) {
            LazyColumn (Modifier.padding(start = 10.dp, end = 10.dp, bottom = 100.dp)) {
                items(participantList) { participant ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clickable {
                                val intent =
                                    Intent(context, ParticipantDetailsActivity::class.java).apply {
                                        putExtra("PARTICIPANT_ID", participant.id.toString())
                                    }
                                context.startActivity(intent)
                            }
                    ) {
                        Text(
                            text = "${participant.firstName} ${participant.lastName}",
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .padding(top = 10.dp),
                            fontSize = 20.sp
                        )
                        Text(
                            text = participant.email,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .padding(bottom = 10.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Jetpack_Compose_APPTheme(dynamicColor = false) {
        DatabaseScreen()
    }
}