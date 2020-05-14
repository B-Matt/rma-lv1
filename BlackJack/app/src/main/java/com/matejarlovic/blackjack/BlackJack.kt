/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:36 PM
 */


package com.matejarlovic.blackjack

import android.annotation.SuppressLint
import android.util.Log

enum class CurrentPlaying {
    PLAYER, DEALER
}

@SuppressLint("SetTextI18n")
class BlackJack {

    private val deck = Deck()
    val dealer = Dealer(deck)
    val player = Player(deck)
    private var currentPlaying: Players? = player

    init {
        // Set dealt cards in hands
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(false))
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(true))


        // Stand Listeners
        player.onStandListener = {
            if(getCurrentPlayer() == CurrentPlaying.PLAYER) {
                Log.d("DEALER", "PLAYING!")
                currentPlaying = dealer
                dealer.play()
            }
        }

        dealer.onStandListener = {
            Log.d("DEALER", "STANDS!")
            if(getCurrentPlayer() == CurrentPlaying.DEALER) {
                currentPlaying = null
            }
        }

        // Hit Listeners
        player.onHitListener = {
            if(getCurrentPlayer() == CurrentPlaying.PLAYER && player.isBust()) {
                currentPlaying = null
            }
        }

        dealer.onHitListener = {
            Log.d("TESTING", getCurrentPlayer().toString())
            if(getCurrentPlayer() == CurrentPlaying.DEALER) {
                if (dealer.isBust()) {
                    Log.d("DEALER", "BUST!")
                    currentPlaying = null
                } else {
                    Log.d("DEALER", "PLAYING!")
                    dealer.play()
                }

            }
        }
        //attachGameListeners()
    }

    // Function that is called when player clicks "reset" button
    fun reset() {
        // Shuffle cards in deck
        deck.shuffle()

        // Reset player states
        //dealer = Dealer(deck)
        //player = Player(deck)
        currentPlaying = player

        // Set dealt cards in hands
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(false))
        player.hand.addCard(deck.deal(false))
        dealer.hand.addCard(deck.deal(true))
    }

    // Function that is called when player clicks "hit" button
    fun playerHit() {
        player.hit()
    }

    // Function that is called when player clicks "stand" button
    fun playerStand() {
        player.stand()
    }

    // Returns who is currently playing dealer or player.
    fun getCurrentPlayer(): CurrentPlaying {
        if(currentPlaying == dealer) {
            return CurrentPlaying.DEALER
        }
        return CurrentPlaying.PLAYER
    }

    // Attaches game listeners
    private fun attachGameListeners() {

    }
}