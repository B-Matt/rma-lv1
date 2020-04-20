package com.matejarlovic.blackjack

sealed class Players(private val deck: Deck) {
    protected var score: Int = 0
    var hand = Hand()
    var onHitListener: (()->Unit)? = null
    var onStandListener: (()->Unit)? = null

    fun hit() {
        hand.addCard(deck.deal())
        score = hand.value()
        onHitListener?.invoke()
    }

    fun stand() {
        onStandListener?.invoke()
    }

    fun isBust(): Boolean {
       return score > 21
    }
}

class Player(deck: Deck): Players(deck) {
    fun score(): Int {
        score = hand.value()
        return score
    }
}


class Dealer(deck: Deck): Players(deck) {
    fun score(hidden: Boolean): String {
        score = hand.value()
        return if(hidden) "?" else score.toString()
    }

    fun score(): Int {
        score = hand.value()
        return score
    }

    fun play() {
        if(score <= 16) {
            hit()
        } else {
            stand()
        }
    }
}