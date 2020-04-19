package com.matejarlovic.yahtzee

import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.marginStart

class Dice(_index: Int, _imageView: ImageView) {

    var isHolding: Boolean = false
    var currentValue: Int = rollDice()

    private val index: Int = _index
    private var imageView: ImageView? = _imageView

    init {
        isHolding = false
        imageView?.setOnClickListener {
            isHolding = true

            val param = imageView?.layoutParams as LinearLayout.LayoutParams
            param.setMargins(10,10,10,10)
            imageView?.layoutParams = param

            Log.d("Dice: " + (index + 1).toString(), "Value: $currentValue")
        }
    }

    fun rollDice(): Int {
        currentValue = (1..6).random()
        return currentValue
    }

    fun setImageView(spriteId: Int) {
        imageView?.setImageResource(spriteId)
    }
}