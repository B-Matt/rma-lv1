/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:24 AM
 */

package com.matejarlovic.yahtzee

import android.widget.ImageView
import android.widget.LinearLayout

class Dice() {

    private var isHolding: Boolean
    private var value: Int

    init {
        isHolding = false
        value = 1;
        rollDice()
    }

    // Gets random value for the dice in range from 1 to 6
    fun rollDice() {
        if(isHolding) {
            return
        }
        value = (1..6).random()
    }

    // Returns current dice value
    fun getValue(): Int {
        return value;
    }

    // Prevents dice to change it's value
    fun hold() {
        isHolding = true;
    }

    // Reset dice state and margins
    fun resetDice() {
        isHolding = false
    }
}