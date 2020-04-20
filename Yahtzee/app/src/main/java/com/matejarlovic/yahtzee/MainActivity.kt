/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:23 AM
 */

package com.matejarlovic.yahtzee

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rollsLeft = 3
        rollsLeftText.text = "Rolls left: $rollsLeft"

        val diceImageViews = listOf<ImageView>(img_dice_1, img_dice_2, img_dice_3, img_dice_4, img_dice_5, img_dice_6)
        val yahtzee = Yahtzee(diceImageViews)

        yahtzee.rollNewGame()

        // Callback listener, called when dice is rolled
        yahtzee.diceRolled = {
            rollHelpText.text = it.first + " (" + it.second + "pts)"
        }

        // Callback listener, called when overall score is updated
        yahtzee.scoreUpdated = {
            scoreText.text = "Current Score: $it"
        }

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
            yahtzee.rollDice()

            rollsLeft -= 1
            rollsLeftText.text = "Rolls left: $rollsLeft"
        }

        // Button callback listener. Called when player clicks new hand button
        newHandButton.setOnClickListener {
            // Reset Roll Counter
            rollsLeft = 3
            rollsLeftText.text = "Rolls left: $rollsLeft"

            // Roll Dices
            yahtzee.rollNewHand()
        }

        // Button callback listener. Called when player clicks new game button
        newGameButton.setOnClickListener {

            // Reset Roll Counter
            rollsLeft = 3
            rollsLeftText.text = "Rolls left: $rollsLeft"

            // Roll Dices
            yahtzee.rollNewGame()
        }
    }
}
