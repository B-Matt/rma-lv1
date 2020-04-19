package com.matejarlovic.yahtzee

import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class Yahtzee(_rolls: Int, _dicesImg: List<ImageView>) {

    private var currentRolls = _rolls;
    private var dices: List<Dice> = List(6) { i -> Dice(i, _dicesImg[i]) }
    private var diceSprites = listOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)


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