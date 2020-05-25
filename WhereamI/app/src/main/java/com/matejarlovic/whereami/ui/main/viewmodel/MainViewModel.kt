package com.matejarlovic.whereami.ui.main.viewmodel

import android.app.Application
import android.location.Address
import androidx.lifecycle.ViewModel
import com.matejarlovic.whereami.data.LocationLiveData
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.*

class MainViewModel(application: Application) : ViewModel() {

    private val location = LocationLiveData(application)

    fun getLocationData() = location
}
