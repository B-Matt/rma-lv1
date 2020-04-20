package com.matejarlovic.blackjack

import android.util.Log

class Hand() {
    private var cards: MutableList<Card> = mutableListOf()
    private var aceNum = 0

    fun addCard(card: Card) {
        if(card.value == 'a') {
            aceNum += 1
        }
        cards.add(card)
    }

    fun cards(): MutableList<Card> {
        return cards
    }

    fun print() {
        for(card in cards) {
            Log.d("HAND", card.toString())
        }
    }

    fun value(): Int {
        val softValue = cards.map { it.value() }.sum()
        val hardValue = softValue + if (aceNum != 0) 10 else 0

        if(hardValue > 21) {
            return hardValue
        } else {
            return softValue
        }
    }
}