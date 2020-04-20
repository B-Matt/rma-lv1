package com.matejarlovic.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deck = Deck()
        val hand = Hand(deck.deal(), deck.deal())
        hand.value()

        hitButton.setOnClickListener {

        }

        standButton.setOnClickListener {

        }
    }
}
