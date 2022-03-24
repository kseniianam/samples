package com.nam.samples

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.nam.samples.google.drive.GoogleDriveViewModel
import com.nam.samples.google.drive.ui.DriveMenuScreen
import com.nam.samples.google.drive.ui.GoogleDriveListener
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {

    private val viewModel: GoogleDriveViewModel by viewModel()

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private val driveListener by lazy {
        object : GoogleDriveListener {
            override fun signIn() {
                val client = GoogleSignIn.getClient(this@MainActivity, viewModel.signInOptions)
                startForResult.launch(client.signInIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriveMenuScreen(driveListener)
        }

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleSignInResult(result.data)
                }
            }

        askPermissions()
    }

    private fun askPermissions() {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        val requestCode = 200
        requestPermissions(permissions, requestCode)
    }

    private fun handleSignInResult(result: Intent?) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener { googleAccount ->
                val credential: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
                    this, Collections.singleton(DriveScopes.DRIVE_FILE)
                )

                credential.selectedAccount = googleAccount.account

                val googleDriveService: Drive = Drive.Builder(
                    NetHttpTransport.Builder().build(),
                    GsonFactory(),
                    credential
                )
                    .setApplicationName("Samples")
                    .build()

                viewModel.onSignIn(googleDriveService)
            }
            .addOnFailureListener {
                    exception -> Log.e(TAG, "Unable to sign in.", exception)
            }
    }
}
