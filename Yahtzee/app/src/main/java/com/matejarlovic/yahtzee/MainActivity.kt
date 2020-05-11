/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:23 AM
 */

package com.matejarlovic.yahtzee

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var diceImageViews: List<ImageView> = listOf()
    private var diceDrawables = listOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)
    private lateinit var yahtzee: Yahtzee

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rollsLeft = 3
        rollsLeftText.text = "Rolls left: $rollsLeft"

        diceImageViews = listOf<ImageView>(img_dice_1, img_dice_2, img_dice_3, img_dice_4, img_dice_5, img_dice_6)
        setDiceListeners()

        yahtzee = Yahtzee()
        updateDiceBitmap()
        yahtzee.rollNewGame()

        // Callback listener, called when player reaches maximum hands
        yahtzee.maxHands = {
            Toast.makeText(this@MainActivity, R.string.app_max_hands, Toast.LENGTH_LONG).show()
        }

        // Button callback listener. Called when player clicks roll button
        rollButton.setOnClickListener {
            if(rollsLeft <= 0) {
                Toast.makeText(this@MainActivity, R.string.app_no_rolls_toast, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            rollingAnim()

            rollsLeft -= 1
            rollsLeftText.text = "Rolls left: $rollsLeft"
        }

        // Button callback listener. Called when player clicks new hand button
        newHandButton.setOnClickListener {
            // Reset Roll Counter
            rollsLeft = 3

            // Roll Dices
            yahtzee.rollNewHand()
            resetDiceSize()
            rollingAnim()

            // Update User Interface
            rollsLeftText.text = "Rolls left: $rollsLeft"
            scoreText.text = "Current score: " + yahtzee.getOverallScore()
        }

        // Button callback listener. Called when player clicks new game button
        newGameButton.setOnClickListener {

            // Reset Roll Counter
            yahtzee.setOverallScore(0)
            rollsLeft = 3

            // Setup User Interface
            scoreText.text = "Current score: " + yahtzee.getOverallScore()
            rollsLeftText.text = "Rolls left: $rollsLeft"

            // Roll Dices
            yahtzee.rollNewGame()
            resetDiceSize()
            rollingAnim()
        }
    }

    // Sets click listeners for the dice image views
    private fun setDiceListeners() {
        for ((index, dice) in diceImageViews.withIndex()) {
            dice.setOnClickListener {
                yahtzee.hold(index)

                val param = dice.layoutParams as LinearLayout.LayoutParams
                param.setMargins(10,10,10,10)
                dice.layoutParams = param
            }
        }
    }

    // Rolls dices every 10 times in 1 second (changes dice drawable every 100ms)
    private fun rollingAnim() {
        val rollAnimMaxDuration = 10
        var rollAnimDuration = 0
        val rollAnimHandler = Handler(Looper.getMainLooper())

        rollAnimHandler.post(object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                rollAnimDuration += 1
                updateDiceBitmap()

                if(rollAnimDuration >= rollAnimMaxDuration) {
                    val rollScore = yahtzee.calculateCurrentScore()
                    yahtzee.updateRollScore(rollScore)

                    rollHelpText.text = rollScore.first + " (" + rollScore.second + "pts)"
                    rollAnimHandler.removeCallbacksAndMessages(null)
                } else {
                    rollAnimHandler.postDelayed(this, 100)
                }
            }
        })
    }

    // Sets dices bitmap with a bitmap of random value
    private fun updateDiceBitmap() {
        for ((index, dice) in yahtzee.getDices().withIndex()) {
            dice.roll()
            diceImageViews[index].setImageResource(diceDrawables[dice.value() - 1])
        }
    }

    // Resets dice's image view margins
    private fun resetDiceSize() {
        for (dice in diceImageViews) {
            val param = dice.layoutParams as LinearLayout.LayoutParams
            param.setMargins(0,0,0,0)
            dice.layoutParams = param
        }
    }
}
