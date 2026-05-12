package md.attendance.sl.di

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionHandler(private val fragment: Fragment) {

    fun checkCameraPermission(onGranted: () -> Unit, onDenied: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
                // You can use the API that requires the permission.
            }

            else -> {
                onDenied()
            }
        }
    }

   fun checkGalleryPermission(onGranted: () -> Unit, onDenied: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
                // You can use the API that requires the permission.
            }

            else -> {
                onDenied()
            }
        }
    }


}