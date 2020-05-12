/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:36 PM
 */


package com.matejarlovic.blackjack

open class Players(protected val deck: Deck) {
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