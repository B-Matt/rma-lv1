package com.matejarlovic.whereami.ui.main.viewmodel

import android.app.Application
import android.location.Address
import androidx.lifecycle.ViewModel
import com.matejarlovic.whereami.data.LocationLiveData
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import com.matejarlovic.whereami.data.AddressLiveData

class MainViewModel(application: Application) : ViewModel() {

    private val location = LocationLiveData(application)
    private var address = AddressLiveData(application)

    fun getLocationData() = location

    fun getAddressData() = address
}
