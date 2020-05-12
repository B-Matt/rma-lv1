/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 5/12/20 7:34 AM
 */

package com.matejarlovic.blackjack

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