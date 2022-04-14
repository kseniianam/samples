package com.nam.samples.google.drive.extensions

import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import java.util.*


private const val SAMPLE_FILE_NAME = "sample_seed_phrase"

fun Drive.uploadSeedPhrase(seedPhrase: String): String {
    val metadata: File = File()
        .setParents(Collections.singletonList("appDataFolder"))
        .setMimeType("text/plain")
        .setName(SAMPLE_FILE_NAME)
        .setCapabilities(File.Capabilities().apply {
            canDelete = false
        })

    val filePath = java.io.File.createTempFile(SAMPLE_FILE_NAME, ".txt").apply {
            writeText(seedPhrase)
        }
    val mediaContent = FileContent("text/plain", filePath)

    val googleFile: File = files().create(metadata, mediaContent)
        .setFields("id")
        .setIgnoreDefaultVisibility(true)
        .setSupportsAllDrives(true)
        .execute()

    return googleFile.id
}

fun Drive.searchFile(id: String) {
    val files: FileList = files().list()
        .setSpaces("appDataFolder")
        .setFields("nextPageToken, files(id, name)")
        .setPageSize(10)
        .execute()

    println(id)

    for (file in files.files) {
        System.out.printf(
            "Found file: %s (%s)\n",
            file.name, file.id
        )
    }
}

private fun Drive.createFolder(): String {
    val fileMetadata = File()
    fileMetadata.name = "Niue"
    fileMetadata.mimeType = "application/vnd.google-apps.folder"

    val file: File = files().create(fileMetadata)
        .setFields("id")
        .execute()

    return file.id
}
