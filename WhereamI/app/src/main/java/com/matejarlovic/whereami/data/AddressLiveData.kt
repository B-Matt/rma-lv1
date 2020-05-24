package com.matejarlovic.whereami.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import java.util.*


class AddressLiveData(context: Context) : LiveData<Address>() {

    private var geocoder = Geocoder(context, Locale.getDefault())

    private fun getAddress(lat:Double, lng:Double) {
        value = geocoder.getFromLocation(lat, lng, 1)[0]
    }
}