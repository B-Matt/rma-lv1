package com.matejarlovic.yahtzee

import android.annotation.SuppressLint
import android.os.Bundle
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
        val yahtzee = Yahtzee(rollsLeft, diceImageViews)
        yahtzee.rollDice()

        rollButton.setOnClickListener {
            if(rollsLeft <= 0) {
                Toast.makeText(this@MainActivity, R.string.app_no_rolls_toast, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            yahtzee.rollDice()
            rollsLeft -= 1
            rollsLeftText.text = "Rolls left: $rollsLeft"
        }
    }
}
