/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:33 PM
 */


package com.matejarlovic.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blackJack = BlackJack(this)

        hitButton.setOnClickListener {
            blackJack.playerHit()
        }

        standButton.setOnClickListener {
            blackJack.playerStand()
        }

        resetButton.setOnClickListener {
            blackJack.reset()
        }
    }
}
