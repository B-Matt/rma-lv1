package com.matejarlovic.yahtzee

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diceImageViews = listOf<ImageView>(img_dice_1, img_dice_2, img_dice_3, img_dice_4, img_dice_5, img_dice_6)
        val yahtzee = Yahtzee(diceImageViews)
        yahtzee.rollDice()

        rollButton.setOnClickListener {
            yahtzee.rollDice()
        }
    }
}
