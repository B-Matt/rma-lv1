/*
 * Created by Matej Arlović
 * Copyright (c)  $today.yea. All rights reserved.
 */


package com.matejarlovic.whereami.ui.main.view

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
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
import com.matejarlovic.whereami.R
import com.matejarlovic.whereami.ui.base.ViewModelFactory
import com.matejarlovic.whereami.ui.main.viewmodel.MainViewModel


class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var youMarker: Marker
    private lateinit var soundPool: SoundPool

    private var pinSound: Int = 0

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

        initSound()
        loadSounds()

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
            playSound(pinSound)
            googleMap.addMarker(
                MarkerOptions().position(it).title("Marker")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
        }
    }

    private fun initSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }
    }

    private fun loadSounds() {
        pinSound = soundPool.load(context, R.raw.pin_sound, 1);
    }

    private fun playSound(sound: Int) {
        soundPool.play(sound, 1f,1f, 0 ,0, 1f)
    }
}
