package md.attendance.sl

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import md.attendance.sl.data.model.LocationResult
import md.attendance.sl.databinding.FragmentMapsBinding
import md.attendance.sl.di.Constants
import md.attendance.sl.di.Extension.setupToolbar

class MapsFragment :
    Fragment(),
    OnMapReadyCallback {

    private var _binding:
            FragmentMapsBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var googleMap:
            GoogleMap

    private lateinit var latLng:
            LatLng

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentMapsBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        setupToolbar(binding.toolbarLayout.toolbar, "Location", true)
        val mapFragment =
            SupportMapFragment.newInstance()

        childFragmentManager
            .beginTransaction()
            .replace(
                binding.mapContainer.id,
                mapFragment
            )
            .commit()

        mapFragment.getMapAsync(
            this
        )
        binding.selectLocationFab.setOnClickListener {
            findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(
                    Constants.LOCATION,
                    LocationResult(latLng.latitude, latLng.longitude)
                )

            findNavController()
                .popBackStack()
        }
    }

    private var selectedMarker: Marker? = null

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onMapReady(
        map: GoogleMap
    ) {
        val latitude: String? =
            arguments
                ?.getString(
                    Constants.LATITUDE
                )
        val longitude: String? =
            arguments
                ?.getString(
                    Constants.LONGITUDE
                )
        if (latitude == null || longitude == null) {
            latLng = LatLng(0.0, 0.0)
        } else {
            latLng = LatLng(latitude.toDouble(), longitude.toDouble())
        }

        googleMap = map

        googleMap.isMyLocationEnabled = true
        changeMarkerPosition(latLng)


        googleMap.setOnMapClickListener {
            selectedMarker?.remove()
            latLng = LatLng(it.latitude, it.longitude)
            changeMarkerPosition(it)

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun changeMarkerPosition(latitude: LatLng) {


        selectedMarker?.remove()

        selectedMarker = googleMap.addMarker(
            MarkerOptions()
                .position(
                    latitude
                )
                .title(
                    "Live location"
                )
        )


        googleMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    latitude,
                    15f
                )
        )
    }
}