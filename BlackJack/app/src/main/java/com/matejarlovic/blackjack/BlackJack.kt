package com.matejarlovic.blackjack

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

@SuppressLint("SetTextI18n")
class BlackJack(private val activity: Activity) {

    private val deck = Deck()
    private val dealer = Dealer(deck)
    private val player = Player(deck)
    private var currentPlaying: Players? = player
    private var playerPlays: Int = 1

    private val dealerScoreTxt = activity.findViewById<TextView>(R.id.dealerScore)
    private val playerScoreTxt = activity.findViewById<TextView>(R.id.playerScore)

    init {
        player.hand.addCard(deck.deal())
        dealer.hand.addCard(deck.deal())
        player.hand.addCard(deck.deal())
        dealer.hand.addCard(deck.deal())

        showHand(R.id.dealerHand, dealer.hand.cards())
        dealerScoreTxt.text = "Dealer score: " + dealer.score(true)

        showHand(R.id.playerHand,  player.hand.cards())
        playerScoreTxt.text = "Player score: " + player.score()

        if(player.isBust()) {
            playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
            showHand(R.id.dealerHand, dealer.hand.cards())
            dealerScoreTxt.text = "Dealer score: " + dealer.score(false)
        }

        if(dealer.isBust()) {
            dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
            playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
            showHand(R.id.dealerHand, dealer.hand.cards())
        }

        // Stand Listeners
        player.onStandListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Stands!")
                currentPlaying = dealer
                dealer.play()
            }
        }

        dealer.onStandListener = {
            if(currentPlaying == dealer) {
                when {
                    dealer.isBust() -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
                    }
                    dealer.score() > player.score() -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
                    }
                    else -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
                    }
                }
            }
        }

        // Hit Listeners
        player.onHitListener = {
            if(currentPlaying == player) {
                Log.d("PLAYER", "Hits!")
                showHand(R.id.playerHand, player.hand.cards())
                playerScoreTxt.text = "Player score: " + player.score()

                if (player.isBust()) {
                    playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
                    dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                    showHand(R.id.dealerHand, dealer.hand.cards())
                    currentPlaying = null
                }
            }
        }

        dealer.onHitListener = {
            if(currentPlaying == dealer) {
                Log.d("DEALER", "Hits!")
                showHand(R.id.dealerHand, dealer.hand.cards())

                when {
                    dealer.isBust() -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
                    }
                    dealer.score() > player.score() -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - WINNER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - LOSER"
                    }
                    else -> {
                        dealerScoreTxt.text = "Dealer score: " + dealer.score(false) + " - LOSER"
                        playerScoreTxt.text = "Player score: " + player.score() + " - WINNER"
                    }
                }
                showHand(R.id.dealerHand, dealer.hand.cards())
                currentPlaying = null
            }
        }
    }

    fun playerHit() {
        player.hit()
    }

    fun playerStand() {
        player.stand()
    }

    private fun showHand(handId: Int, cards: MutableList<Card>) {
        val linearLayout = activity.findViewById<LinearLayout>(handId)
        linearLayout.removeAllViews()

        for(card in cards) {
            val imageView = ImageView(activity)
            val density = activity.resources.displayMetrics.density
            imageView.layoutParams = LinearLayout.LayoutParams((90 * density).toInt(), (110 * density).toInt())

            val imgResId = activity.resources.getIdentifier(card.toString(), "drawable", activity.packageName)
            imageView.setImageResource(imgResId)
            linearLayout?.addView(imageView)
        }
    }
}