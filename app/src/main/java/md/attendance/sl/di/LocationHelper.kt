package md.attendance.sl.di

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationHelper(private val fragment: Fragment) {

    private var onGranted:
            (() -> Unit)? = null

    private var onDenied:
            ((String) -> Unit)? = null

    private var onDeniedPermanently:
            ((String) -> Unit)? = null
    private var
            currentPermission:
            String = ""
    private val permissionLauncher:
            ActivityResultLauncher<String> =
        fragment.registerForActivityResult(
            ActivityResultContracts
                .RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                onGranted?.invoke()

            } else {

                val permanentlyDenied =
                    !fragment
                        .shouldShowRequestPermissionRationale(
                            currentPermission
                        )

                if (
                    permanentlyDenied
                ) {
                    onDeniedPermanently?.invoke("Permission permanently denied. Enable from settings.")


                } else {

                    onDenied?.invoke(
                        "Permission denied"
                    )
                }
            }
        }

    fun requestPermission(
        permission: String,
        onGranted: () -> Unit,
        onDenied:
            (message: String)
        -> Unit
    ) {

        currentPermission =
            permission
        val isGranted = ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        this.onGranted =
            onGranted

        this.onDenied =
            onDenied
        if (isGranted) {

        }
        permissionLauncher
            .launch(permission)
    }

    @SuppressLint("MissingPermission")
    fun getLiveLocation(
        onResult:
            (
            latitude: Double,
            longitude: Double
        ) -> Unit
    ) {
        val request =
            CurrentLocationRequest
                .Builder()
                .setPriority(
                    Priority
                        .PRIORITY_HIGH_ACCURACY
                )
                .setMaxUpdateAgeMillis(
                    0
                )
                .build()
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(fragment.requireActivity())

        fusedLocationClient.getCurrentLocation(
            request,
            null
        ).addOnSuccessListener {
            it?.let { location ->

                onResult(
                    location.latitude,
                    location.longitude
                )
            }
        }
    }


}

