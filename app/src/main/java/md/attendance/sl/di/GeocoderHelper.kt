package md.attendance.sl.di

import android.content.Context
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

object GeocoderHelper {

    suspend fun getAddressFromLatLng(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String? =
        try {
            withContext(Dispatchers.IO){
                val geocoder =
                    Geocoder(
                        context,
                        Locale.getDefault()
                    )

                val addresses =
                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    )
                addresses
                    ?.firstOrNull()
                    ?.getAddressLine(0)
            }
        }catch (
            e: Exception
        ) {
            Log.e("GeocoderHelper", "Error getting address: ${e.message}")
            null
        }


}