package com.example.technicaltest.presentation.ui.fragments.location

import android.Manifest
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.technicaltest.R
import com.example.technicaltest.databinding.FragmentLocationBinding
import com.example.technicaltest.services.LocationService
import com.example.technicaltest.services.LocationService.Companion.ACTION_START_LOCATION_SERVICE
import com.example.technicaltest.services.LocationService.Companion.TAG_SERVICE_LOCATION
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback {

    // Binding
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: LocationViewModel by viewModels()

    // Permissions Launcher
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // Validate Permissions
    private var hasLocationCoarse = false
    private var hasLocationFine = false

    // Map
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            it.forEach { (permission, granted) ->
                when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> hasLocationCoarse = granted
                    Manifest.permission.ACCESS_FINE_LOCATION -> hasLocationFine = granted
                }
            }
            if (!hasLocationCoarse || !hasLocationFine) {
                showAlertDialog()
            }
            else {
                broadcastReceiver()
            }
        }

        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllLocations(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun broadcastReceiver() {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val latitude = intent?.getStringExtra("latitude")?.toDouble()
                val longitude = intent?.getStringExtra("longitude")?.toDouble()
                if (latitude != null && longitude != null) {
                    Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
                    val currentDate = viewModel.getCurrentDate()
                    val newLocation = com.example.technicaltest.domain.location.Location().apply {
                        this.latitude = latitude
                        this.longitude = longitude
                        this.date = currentDate
                    }
                    viewModel.saveLocation(newLocation, view!!)
                }
            }
        }
        val intentFilter = IntentFilter("android.intent.action.NEW_LOCATION")
        requireActivity().registerReceiver(broadcastReceiver, intentFilter)

        if (!LocationService.isRunning) {
            val intent = Intent(requireContext(), LocationService::class.java)
            intent.putExtra(TAG_SERVICE_LOCATION, ACTION_START_LOCATION_SERVICE)
            requireContext().startService(intent)
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permisos denegados")
        builder.setMessage("Tu necesitas dar permisos para acceder a la ubicación")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        viewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            // remove all markers
            mMap.clear()
            locations.forEach { location ->
                val latLng = LatLng(location.latitude!!, location.longitude!!)
                mMap.addMarker(MarkerOptions().position(latLng).title(location.date))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18f))
            }
        })
    }
}