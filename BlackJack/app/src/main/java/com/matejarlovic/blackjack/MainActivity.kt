package com.matejarlovic.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
