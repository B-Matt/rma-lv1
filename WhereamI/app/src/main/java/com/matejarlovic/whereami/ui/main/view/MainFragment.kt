package com.matejarlovic.whereami.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.matejarlovic.whereami.BR
import com.matejarlovic.whereami.R
import com.matejarlovic.whereami.ui.base.ViewModelFactory
import com.matejarlovic.whereami.ui.main.viewmodel.MainViewModel
import com.matejarlovic.whereami.utils.GpsUtils
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ViewDataBinding

    private var isGSPEnabled = false
    private var mapReady = false

    private val LOCATION_REQUEST = 100
    private val GPS_REQUEST = 101

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //val rootView = inflater.inflate(R.layout.main_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        /*val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment

        mapFragment.getMapAsync {
            googleMap = it
            mapReady = true
        }*/
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(activity!!.application)
        ).get(MainViewModel::class.java)

        binding.setVariable(BR.locationViewModel, viewModel)

        if(context == null) {
            return
        }

        GpsUtils(context!!).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                this@MainFragment.isGSPEnabled = isGPSEnable
            }
        })
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    private fun invokeLocationAction() {
        when {
            !isGSPEnabled -> return
            isPermissionsGranted() -> startLocationUpdate()
            shouldShowRequestPermissionRationale() -> return

            else -> ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            latitudeText.text = it.latitude.toString()
            longitudeText.text = it.longitude.toString()
        })

        viewModel.getAddressData().observe(this, Observer {
            Log.d("TESTING", it.toString())
        })
    }

    private fun isPermissionsGranted(): Boolean {
        if(context == null) {
            return false
        }
        return ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        if(activity == null) {
            return false
        }
        return ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}
