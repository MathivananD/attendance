package md.attendance.sl.di

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationPermission(private val context: Context) {

    private val fusedLocationClient =
        LocationServices
            .getFusedLocationProviderClient(
                context
            )

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onSuccess:
            (
            latitude: Double,
            longitude: Double,
        ) -> Unit,
        onFailure:
            (
            error: String,
        ) -> Unit,
    ) {
        fusedLocationClient
            .getCurrentLocation(
                Priority
                    .PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener {
                if (it != null) {

                    onSuccess(
                        it.latitude,
                        it.longitude
                    )

                } else {

                    onFailure(
                        "Location not available"
                    )
                }
            }.addOnFailureListener {

                onFailure(
                    it.message
                        ?: "Unknown error"
                )
            }
    }

}