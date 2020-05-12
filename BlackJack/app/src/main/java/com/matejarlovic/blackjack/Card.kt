/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 6:05 PM
 */


package com.matejarlovic.blackjack

// Holds card data (suit (eg. D,H,C, etc.) and value (eg. 2,3,4,10,etc.))
data class Card(val suit: Char, val value: Char, var hidden: Boolean) {
    // Returns value of card as integer (special cards also have integer value)
    fun getValue(): Int {
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