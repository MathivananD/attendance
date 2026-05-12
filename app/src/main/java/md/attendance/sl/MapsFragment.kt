package md.attendance.sl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import md.attendance.sl.databinding.FragmentMapsBinding

class MapsFragment :
    Fragment(),
    OnMapReadyCallback {

    private var _binding:
            FragmentMapsBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var googleMap:
            GoogleMap

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
    }

    override fun onMapReady(
        map: GoogleMap
    ) {

        googleMap = map

        val coimbatore =
            LatLng(
                11.0168,
                76.9558
            )

        googleMap.addMarker(
            MarkerOptions()
                .position(
                    coimbatore
                )
                .title(
                    "Coimbatore"
                )
        )

        googleMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    coimbatore,
                    15f
                )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}