package com.kssidll.opierdalo.domain.image

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageManager(private val context: Context) {

    private val imageDirectory: File by lazy {
        File(context.filesDir, "images").apply { mkdirs() }
    }

    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File(imageDirectory, "JPEG_${timeStamp}.jpg")
    }

    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun deleteImage(uri: Uri) {
        val file = File(uri.path ?: return)
        if (file.exists()) {
            file.delete()
        }
    }

    fun getAllImageUris(): List<Uri> {
        return imageDirectory.listFiles()
            ?.filter { it.isFile && it.name.endsWith(".jpg", ignoreCase = true) }
            ?.map { getUriForFile(it) }
            ?: emptyList()
    }
}