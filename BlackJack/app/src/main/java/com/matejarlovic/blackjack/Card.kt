/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:05 PM
 */


package com.matejarlovic.blackjack

import android.util.Log

// Holds card data (suit (eg. D,H,C, etc.) and value (eg. 2,3,4,10,etc.))
data class Card(val suit: Char, val value: Char, var hidden: Boolean) {
    // Returns value of card as integer (special cards also have integer value)
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

    // Returns card data (suit + value) as a string
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
        VALUES.forEach {  value -> SUITS.forEach { suit -> cards.add(Card(suit, value, false)) } }
    }

    // Shuffles cards inside deck
    fun shuffle() {
        cards.shuffle()
    }

    // Deals cards inside deck (from top)
    fun deal(hidden: Boolean): Card {
        dealtCards += 1
        cards[dealtCards - 1].hidden = hidden
        return cards[dealtCards - 1]
    }
}