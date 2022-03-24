package com.nam.samples.google.drive.di

import com.nam.samples.google.drive.GoogleDriveViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val googleDriveModule = module {

    viewModel {
        GoogleDriveViewModel()
    }
}