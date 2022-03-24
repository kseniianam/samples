package com.nam.samples.google.drive.extensions

import android.os.Environment
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import java.util.*

private const val SAMPLE_FILE_NAME = "sample_seed_phrase"

fun Drive.uploadSeedPhrase(seedPhrase: String): String {
    val metadata: File = File()
        .setParents(Collections.singletonList("root"))
        .setMimeType("text/plain")
        .setName(SAMPLE_FILE_NAME)

    val filePath = java.io.File(Environment.getExternalStorageDirectory(), SAMPLE_FILE_NAME).apply {
        createNewFile()
        writeText(seedPhrase)
    }
    val mediaContent = FileContent("text/plain", filePath)

    val googleFile: File = files().create(metadata, mediaContent)
        .setFields("id")
        .execute()

    return googleFile.id
}
