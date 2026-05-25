package md.attendance.sl.ui.map

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import md.attendance.sl.ui.history.recycleview.HistoryRecycleView
import md.attendance.sl.ui.map.list.SearchPlacesRecycleView

class MapsFragment :
    Fragment(),
    OnMapReadyCallback {

    private var _binding:
            FragmentMapsBinding? = null

    private lateinit var searchAdapter:
            SearchPlacesRecycleView
    private val binding
        get() = _binding!!

    private lateinit var googleMap:
            GoogleMap

    private lateinit var latLng:
            LatLng
    private val allPlaces = mutableListOf(
        "Bangalore",
        "Bangalore Palace",
        "Bangalore Airport",
        "Bannerghatta"
    )
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
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
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
        searchAdapter= SearchPlacesRecycleView(list = mutableListOf())
        binding.rvSearch.adapter =
            searchAdapter
        binding.searchView
            .setOnQueryTextListener(
                object :
                    SearchView.OnQueryTextListener {

                    override fun onQueryTextChange(
                        newText: String?
                    ): Boolean {

                        val query =
                            newText.orEmpty()


                        if (query.isEmpty()) {

                            binding.rvSearch.visibility =
                                View.GONE

                        } else {
                            binding.rvSearch.visibility =
                                View.VISIBLE
                            Log.e("sdfsfsfsdf","${allPlaces.size}")
                            val filteredList =
                                allPlaces.filter {
                                    it.contains(
                                        query,
                                        ignoreCase = true
                                    )
                                }

                            searchAdapter.updateList(
                                filteredList
                            )

                            binding.rvSearch.visibility =
                                if (filteredList.isEmpty())
                                    View.GONE
                                else
                                    View.VISIBLE
                        }

                        return true
                    }

                    override fun onQueryTextSubmit(
                        query: String?
                    ): Boolean {

                        binding.rvSearch.visibility =
                            View.GONE

                        return true
                    }
                }
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