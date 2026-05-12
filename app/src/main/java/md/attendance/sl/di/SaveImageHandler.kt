package md.attendance.sl.di

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
data class FileResult(
    val uri: Uri?,
    val path: String?
)
object SaveImageHandler {
    private const val FOLDER_NAME = "profile_images"
    var currentImagePath: String? = null
    fun saveImageToFolder(
        context: Context,
        uri: Uri
    ): String? {
        try {
            val extension = getFileExtension(context, uri)
            Log.e("Save to image gallery", "${extension}")
            val inputStream = context.contentResolver.openInputStream(uri)
            val folder = File(context.filesDir, FOLDER_NAME)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val file = File(folder, "profile_${System.currentTimeMillis()}.${extension}")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            return file.absolutePath
        } catch (e: Exception) {
            Log.e("Save to image gallery exception", "${e.message}")
            return null;
        }

    }

    fun createImageFile(
        context: Context
    ): FileResult? {

        return try {

            val folder =
                File(
                    context.filesDir,
                    FOLDER_NAME
                )

            if (!folder.exists()) {
                folder.mkdirs()
            }

            val file =
                File(
                    folder,
                    "${FOLDER_NAME}_${System.currentTimeMillis()}.jpg"
                )

            currentImagePath =
                file.absolutePath

           val uril= FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            FileResult(uril,file.absolutePath)

        } catch (e: Exception) {

            Log.e(
                "Camera Exception",
                e.message ?: ""
            )

            null
        }
    }

    fun getFileExtension(
        context: Context,
        uri: Uri
    ): String? {

        val mimeType =
            context.contentResolver
                .getType(uri)

        return MimeTypeMap
            .getSingleton()
            .getExtensionFromMimeType(
                mimeType
            )
    }

}