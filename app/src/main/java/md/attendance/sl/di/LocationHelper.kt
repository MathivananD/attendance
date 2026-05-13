package md.attendance.sl.di

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission

object LocationHelper {

    fun checkPermission(context: Context) {
     val temp=   checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }



}