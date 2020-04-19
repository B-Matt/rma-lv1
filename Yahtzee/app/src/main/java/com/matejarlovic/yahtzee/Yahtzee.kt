package com.matejarlovic.yahtzee

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class Yahtzee(_dicesImg: List<ImageView>) {

    var dices: List<Dice> = List(6) { i -> Dice(i, _dicesImg[i]) }
    var diceSprites = listOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)

    fun rollDice() {

        val rollAnimMaxDuration = 10
        var rollAnimDuration = 0
        val rollAnimHandler = Handler(Looper.getMainLooper())

        rollAnimHandler.post(object : Runnable {
            override fun run() {
                rollAnimDuration += 1
                updateDiceBitmap()

                if(rollAnimDuration >= rollAnimMaxDuration) {
                    rollAnimHandler.removeCallbacksAndMessages(null)
                } else {
                    rollAnimHandler.postDelayed(this, 100)
                }
            }
        })
    }

    fun updateDiceBitmap() {

        for (dice in dices) {

            if(!dice.isHolding) {
                val value = dice.rollDice()
                dice.setImageView(diceSprites[value - 1])
            }
        }
    }
}