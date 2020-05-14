/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 5/12/20 7:31 AM
 */

package com.matejarlovic.blackjack

import android.util.Log

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
            Log.d("DEALER", "Hits!")
            hit()
        } else {
            Log.d("DEALER", "Stands!")
            stand()
        }
    }
}