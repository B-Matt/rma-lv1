package com.matejarlovic.blackjack

import android.util.Log

class Hand(card1: Card, card2: Card) {
    private var cards: MutableList<Card> = mutableListOf()
    private var aceNum = 0

    init {
        addCard(card1)
        addCard(card2)
    }

    fun addCard(card: Card) {
        if(card.value == 'a') {
            aceNum += 1
        }
        cards.add(card)
    }

    fun value() {
        Log.d("CARD VALUE 1", cards[0].toString())
        Log.d("CARD VALUE 2", cards[1].toString())
    }
}