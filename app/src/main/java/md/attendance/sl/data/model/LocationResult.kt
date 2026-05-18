package md.attendance.sl.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationResult(
    val latitude: Double,
    val longitude: Double
) : Parcelable
