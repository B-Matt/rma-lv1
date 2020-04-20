package com.matejarlovic.blackjack

import android.util.Log

data class Card(val suit: Char, val value: Char) {

    fun value(): Int {
        return when (value) {
            't' -> 10
            'a' -> 1
            'j' -> 12
            'q' -> 13
            'k' -> 14
            else -> Character.getNumericValue(value)
        }
    }

    override fun toString(): String {
        return suit + "" + value
    }
}

class Deck {
    private var cards: MutableList<Card> = mutableListOf()
    private val VALUES = "23456789tjqka"
    private val SUITS = "shdc"
    private var dealtCards: Int = 0

    init {
        VALUES.forEach {  value -> SUITS.forEach { suit -> cards.add(Card(suit, value)) } }
        cards.shuffle()
    }

    fun deal(): Card {
        dealtCards += 1
        return cards[dealtCards - 1]
    }

    fun print() {
        for((index, card) in cards.withIndex()) {
            Log.d("CARD $index", card.suit + "" + card.value)
        }
    }
}