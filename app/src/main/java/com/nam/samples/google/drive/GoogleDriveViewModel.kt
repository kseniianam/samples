package com.nam.samples.google.drive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.nam.samples.google.drive.extensions.uploadSeedPhrase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GoogleDriveViewModel : ViewModel() {

    private companion object {
        const val SEED_PHRASE = "seed phrase text"
    }

    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("735323908781-533nfjvakc5chg9j8dvoogr56uj9u6si.apps.googleusercontent.com")
        .requestEmail()
        .requestScopes(Scope(DriveScopes.DRIVE_FILE))
        .build()

    fun onSignIn(driveService: Drive) {
        viewModelScope.launch(Dispatchers.IO) {
            driveService.uploadSeedPhrase(SEED_PHRASE)
        }
    }
}
