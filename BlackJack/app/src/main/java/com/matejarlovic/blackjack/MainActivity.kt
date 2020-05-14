/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:33 PM
 */


package com.matejarlovic.blackjack

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val blackJack = BlackJack()
    private var dealerScoreTxt: TextView? = null
    private var playerScoreTxt: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reference UI
        dealerScoreTxt = findViewById(R.id.dealerScore)
        playerScoreTxt = findViewById(R.id.playerScore)

        dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(true)
        playerScoreTxt?.text = "Player score: " + blackJack.player.score()

        // Game Listeners
        attachGameListeners()

        // Show cards in hand on the user interface
        showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), true)
        showHand(R.id.playerHand,  blackJack.player.hand.cards(), true)

        // UI Listeners
        hitButton.setOnClickListener {
            blackJack.playerHit()
            playerScoreTxt?.text = "Player score: " + blackJack.player.score()
            showHand(R.id.playerHand, blackJack.player.hand.cards(), false)

            if (blackJack.player.isBust()) {
                showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), false)
                playerScoreTxt?.text = "Player score: " + blackJack.player.score() + " - LOSER"
                dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(false) + " - WINNER"
            }
        }

        standButton.setOnClickListener {
            blackJack.playerStand()
        }

        resetButton.setOnClickListener {
            blackJack.reset()

            // Show cards in hand on the user interface
            showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), true)
            dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(true)

            showHand(R.id.playerHand,  blackJack.player.hand.cards(), true)
            playerScoreTxt?.text = "Player score: " + blackJack.player.score()
        }
    }

    // Checks winning conditions and writes down who is winner
    @SuppressLint("SetTextI18n")
    private fun writeWinner() {
        when {
            blackJack.dealer.isBust() -> {
                dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(false) + " - LOSER"
                playerScoreTxt?.text = "Player score: " + blackJack.player.score() + " - WINNER"
            }
            blackJack.dealer.score() > blackJack.player.score() -> {
                dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(false) + " - WINNER"
                playerScoreTxt?.text = "Player score: " + blackJack.player.score() + " - LOSER"
            }
            else -> {
                dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score(false) + " - LOSER"
                playerScoreTxt?.text = "Player score: " + blackJack.player.score() + " - WINNER"
            }
        }
    }

    // Shows cards in hand on the user interface
    private fun showHand(handId: Int, cards: MutableList<Card>, checkHiding: Boolean) {
        val layout = findViewById<androidx.gridlayout.widget.GridLayout>(handId)
        layout.removeAllViews()

        for(card in cards) {
            val imageView = ImageView(this)
            val density = resources.displayMetrics.density
            imageView.layoutParams = LinearLayout.LayoutParams((70 * density).toInt(), (90 * density).toInt())

            val imgResId = resources.getIdentifier(if(checkHiding && card.hidden) "h" else card.toString(), "drawable", packageName)
            imageView.setImageResource(imgResId)
            layout?.addView(imageView)
        }
    }

    // Attaches game listeners
    @SuppressLint("SetTextI18n")
    private fun attachGameListeners() {
        blackJack.dealer.onStandListener = {
            Log.d("TESTING1", "Dealer Stands!")
            if(blackJack.getCurrentPlayer() == CurrentPlaying.DEALER) {
                showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), false)
                writeWinner()
                showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), false)
            }
        }

        blackJack.dealer.onHitListener = {
            if(blackJack.getCurrentPlayer() == CurrentPlaying.DEALER) {
                showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), true)
                dealerScoreTxt?.text = "Dealer score: " + blackJack.dealer.score()

                if (blackJack.dealer.isBust()) {
                    showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), false)
                    writeWinner()
                    showHand(R.id.dealerHand, blackJack.dealer.hand.cards(), false)
                }
            }
        }
    }
}
