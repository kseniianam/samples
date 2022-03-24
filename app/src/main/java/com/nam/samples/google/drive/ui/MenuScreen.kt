package com.nam.samples.google.drive.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.SignInButton

@ExperimentalComposeUiApi
@Preview
@Composable
private fun PreviewDriveMenuScreen() {
    DriveMenuScreen(driveListener = object : GoogleDriveListener {
        override fun signIn() {
            TODO("Not yet implemented")
        }
    })
}


@ExperimentalComposeUiApi
@Composable
internal fun DriveMenuScreen(driveListener: GoogleDriveListener) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            factory = { context ->
                SignInButton(context).apply {
                    setOnClickListener {
                        driveListener.signIn()
                    }
                    setSize(SignInButton.SIZE_WIDE)
                }
            })
    }
}