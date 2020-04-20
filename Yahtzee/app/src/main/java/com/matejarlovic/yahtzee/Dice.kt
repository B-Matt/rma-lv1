/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:24 AM
 */

package com.matejarlovic.yahtzee

import android.widget.ImageView
import android.widget.LinearLayout

class Dice(_imageView: ImageView) {

    var isHolding: Boolean
    var value: Int

    private var imageView: ImageView? = _imageView

    init {
        isHolding = false
        value = rollDice()

        imageView?.setOnClickListener {
            isHolding = true

            val param = imageView?.layoutParams as LinearLayout.LayoutParams
            param.setMargins(10,10,10,10)
            imageView?.layoutParams = param
        }
    }

    // Gets random value for the dice in range from 1 to 6
    fun rollDice(): Int {
        value = (1..6).random()
        return value
    }

    // Reset dice state and margins
    fun resetDice() {
        isHolding = false

        val param = imageView?.layoutParams as LinearLayout.LayoutParams
        param.setMargins(0,0,0,0)
        imageView?.layoutParams = param
    }

    // Changes bitmap inside ImageView with given sprite
    fun setImageView(spriteId: Int) {
        imageView?.setImageResource(spriteId)
    }
}