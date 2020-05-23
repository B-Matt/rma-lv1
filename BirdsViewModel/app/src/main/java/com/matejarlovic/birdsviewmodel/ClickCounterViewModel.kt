package com.matejarlovic.birdsviewmodel

import android.graphics.Color
import androidx.lifecycle.ViewModel

class ClickCounterViewModel: ViewModel() {

    private var counter: Int = 0
    private var background: Int = Color.WHITE

    fun setCount(count: Int) {
        this.counter = count
    }

    fun getCount(): Int {
        return this.counter
    }

    fun setColor(color: Int) {
        this.background = color
    }

    fun getColor(): Int {
        return this.background
    }
}