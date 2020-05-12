/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 5/12/20 7:30 AM
 */

package com.matejarlovic.blackjack

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