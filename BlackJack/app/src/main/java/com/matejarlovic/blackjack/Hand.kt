/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:36 PM
 */


package com.matejarlovic.blackjack

class Hand() {
    private var cards: MutableList<Card> = mutableListOf()
    private var aceNum = 0

    // Adds new card in player's hand
    fun addCard(card: Card) {
        if(card.value == 'a') {
            aceNum += 1
        }
        cards.add(card)
    }

    // Returns private cards variable in player's hand
    fun cards(): MutableList<Card> {
        return cards
    }

    // Returns value of all cards in player's hand
    fun value(): Int {
        val softValue = cards.map { it.getValue() }.sum()
        val hardValue = softValue + if (aceNum != 0) 10 else 0

        if(hardValue > 21) {
            return hardValue
        } else {
            return softValue
        }
    }
}