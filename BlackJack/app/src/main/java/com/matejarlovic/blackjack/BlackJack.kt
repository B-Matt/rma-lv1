/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:36 PM
 */


package com.matejarlovic.blackjack

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

@SuppressLint("SetTextI18n")
class BlackJack(private val activity: Activity) {

    private val deck = Deck()
    private var dealer = Dealer(deck)
    private var player = Player(deck)
    private var currentPlaying: Players? = player

    private val dealerScoreTxt = activity.findViewById<TextView>(R.id.dealerScore)
    private val playerScoreTxt = activity.findViewById<TextView>(R.id.playerScore)

    init {
        // Set dealt cards in hands
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(false))
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(true))

        // Show cards in hand on the user interface
        showHand(R.id.dealerHand, dealer.hand.cards(), true)
        dealerScoreTxt.text = "Dealer score: " + dealer.score(true)

        showHand(R.id.playerHand,  player.hand.cards(), true)
        playerScoreTxt.text = "Player score: " + player.score()

        // Stand Listeners
        player.onStandListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Stands!")
                currentPlaying = dealer
                dealer.play()
            }
        }

        dealer.onStandListener = {
            if(currentPlaying == dealer) {
                Log.d("DEALER", "Stands!")
                showHand(R.id.dealerHand, dealer.hand.cards(), false)
                writeWinner()
                showHand(R.id.dealerHand, dealer.hand.cards(), false)
                currentPlaying = null
            }
        }

        // Hit Listeners
        player.onHitListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Hits!")
                showHand(R.id.playerHand, player.hand.cards(), false)
                playerScoreTxt.text = "Player score: " + player.score()

                if (player.isBust()) {
                    playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
                    dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    currentPlaying = null
                }
            }
        }

        dealer.onHitListener = {
            if(currentPlaying == dealer) {
                Log.d("DEALER", "Hits!")
                showHand(R.id.dealerHand, dealer.hand.cards(), true)
                dealerScoreTxt.text = "Dealer score: " + dealer.score()

                if (dealer.isBust()) {
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    writeWinner()
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    currentPlaying = null
                } else {
                    dealer.play()
                }

            }
        }
    }

    // Function that is called when player clicks "reset" button
    fun reset() {
        // Shuffle cards in deck
        deck.shuffle()

        // Reset player states
        dealer = Dealer(deck)
        player = Player(deck)
        currentPlaying = player

        // Set dealt cards in hands
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(false))
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(true))

        // Show cards in hand on the user interface
        showHand(R.id.dealerHand, dealer.hand.cards(), true)
        dealerScoreTxt.text = "Dealer score: " + dealer.score(true)

        showHand(R.id.playerHand,  player.hand.cards(), true)
        playerScoreTxt.text = "Player score: " + player.score()

        // Stand Listeners
        player.onStandListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Stands!")
                currentPlaying = dealer
                dealer.play()
            }
        }

        dealer.onStandListener = {
            if(currentPlaying == dealer) {
                Log.d("DEALER", "Stands!")
                showHand(R.id.dealerHand, dealer.hand.cards(), false)
                writeWinner()
                showHand(R.id.dealerHand, dealer.hand.cards(), false)
                currentPlaying = null
            }
        }

        // Hit Listeners
        player.onHitListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Hits!")
                showHand(R.id.playerHand, player.hand.cards(), false)
                playerScoreTxt.text = "Player score: " + player.score()

                if (player.isBust()) {
                    playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
                    dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    currentPlaying = null
                }
            }
        }

        dealer.onHitListener = {
            if(currentPlaying == dealer) {
                Log.d("DEALER", "Hits!")
                showHand(R.id.dealerHand, dealer.hand.cards(), true)
                dealerScoreTxt.text = "Dealer score: " + dealer.score()

                if (dealer.isBust()) {
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    writeWinner()
                    showHand(R.id.dealerHand, dealer.hand.cards(), false)
                    currentPlaying = null
                } else {
                    dealer.play()
                }

            }
        }
    }

    // Function that is called when player clicks "hit" button
    fun playerHit() {
        player.hit()
    }

    // Function that is called when player clicks "stand" button
    fun playerStand() {
        player.stand()
    }

    // Checks winning conditions and writes down who is winner
    private fun writeWinner() {
        when {
            dealer.isBust() -> {
                dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
            }
            dealer.score() > player.score() -> {
                dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
            }
            else -> {
                dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
            }
        }
    }

    // Shows cards in hand on the user interface
    private fun showHand(handId: Int, cards: MutableList<Card>, checkHiding: Boolean) {
        val layout = activity.findViewById<androidx.gridlayout.widget.GridLayout>(handId)
        layout.removeAllViews()

        for(card in cards) {
            val imageView = ImageView(activity)
            val density = activity.resources.displayMetrics.density
            imageView.layoutParams = LinearLayout.LayoutParams((70 * density).toInt(), (90 * density).toInt())

            val imgResId = activity.resources.getIdentifier(if(checkHiding && card.hidden) "h" else card.toString(), "drawable", activity.packageName)
            imageView.setImageResource(imgResId)
            layout?.addView(imageView)
        }
    }
}