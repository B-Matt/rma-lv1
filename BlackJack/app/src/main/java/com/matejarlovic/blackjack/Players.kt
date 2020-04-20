/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:36 PM
 */


package com.matejarlovic.blackjack

sealed class Players(protected val deck: Deck) {
    protected var score: Int = 0
    var hand = Hand()
    var onHitListener: (()->Unit)? = null
    var onStandListener: (()->Unit)? = null

    // Add new card in player's hands, updates score and calls onHitListener for current player
    open fun hit() {
        hand.addCard(deck.deal(true))
        score = hand.value()
        onHitListener?.invoke()
    }

    // Calls onStandListener for current player
    fun stand() {
        onStandListener?.invoke()
    }

    // Checks if player's score is above 21 and returns boolean
    fun isBust(): Boolean {
       return score > 21
    }
}

class Player(deck: Deck): Players(deck) {
    // Returns score of player's hand
    fun score(): Int {
        score = hand.value()
        return score
    }

    // Add new card in player's hands, updates score and calls onHitListener for current player
    override fun hit() {
        hand.addCard(deck.deal(false))
        score = hand.value()
        onHitListener?.invoke()
    }
}

class Dealer(deck: Deck): Players(deck) {
    // Returns dealer's score if game is not hiding that information from player otherwise it only returns '?'
    fun score(hidden: Boolean): String {
        score = hand.value()
        return if(hidden) "?" else score.toString()
    }

    // Returns dealer's score as integer
    fun score(): Int {
        score = hand.value()
        return score
    }

    // Dealer's play function in which it decide if it wants to hit or stand
    fun play() {
        if(score <= 16) {
            hit()
        } else {
            stand()
        }
    }
}