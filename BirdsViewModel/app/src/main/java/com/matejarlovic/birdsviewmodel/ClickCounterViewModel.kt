package com.matejarlovic.birdsviewmodel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClickCounterViewModel: ViewModel() {

    private var counter: Int = 0
    private var background: Int = Color.WHITE

    private var counterLiveData = MutableLiveData<Int>()
    private var backgroundLiveData = MutableLiveData<Int>()

    // Counting
    fun getInitialCount(): MutableLiveData<Int> {
        counterLiveData.value = counter
        return counterLiveData
    }

    fun setCount(count: Int) {
        this.counter = count
        counterLiveData.value = count
    }

    fun getCount(): Int {
        return this.counter
    }

    // Background Color
    fun getInitialColor(): MutableLiveData<Int> {
        backgroundLiveData.value = background
        return backgroundLiveData
    }

    fun setColor(color: Int) {
        this.background = color
        backgroundLiveData.value = color
    }
}