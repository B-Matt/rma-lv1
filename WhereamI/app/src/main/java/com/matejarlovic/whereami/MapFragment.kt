package com.matejarlovic.whereami

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.matejarlovic.whereami.ui.base.ViewModelFactory
import com.matejarlovic.whereami.ui.main.viewmodel.MainViewModel


class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var youMarker: Marker

    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.main_activity, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment

        mapFragment.getMapAsync {
            googleMap = it
            mapReady = true

            youMarker = googleMap.addMarker(
                MarkerOptions().position(LatLng(0.0, 0.0)).title("You")
            )
            setMapClickEvent()
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity: FragmentActivity =
            activity ?: throw IllegalArgumentException("Activity is null!")
        viewModel = ViewModelProvider(
            activity,
            ViewModelFactory(activity.application)
        ).get(MainViewModel::class.java)

        viewModel.getLocationData()
            .observe(
                activity
                , Observer {
                    val newPosition = LatLng(it.latitude, it.longitude)
                    youMarker.position = newPosition
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 16f));
                })
    }

    private fun setMapClickEvent() {
        googleMap.setOnMapClickListener {
            // TODO: Dodati sound
            googleMap.addMarker(
                MarkerOptions().position(it).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }
}
