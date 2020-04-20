/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:24 AM
 */

package com.matejarlovic.yahtzee

import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class Yahtzee(_dicesImg: List<ImageView>) {

    var diceRolled: ((Pair<String, Int>) -> Unit)? = null
    var scoreUpdated: ((Int) -> Unit)? = null
    var maxHands: (() -> Unit)? = null

    private var overallScore: Int
    private var dices: List<Dice>
    private var diceDrawables: List<Int>
    private var scoreList: ArrayList<String>
    private lateinit var rollScore: Pair<String, Int>

    init {
        diceRolled = null
        scoreUpdated = null

        overallScore = 0
        dices = List(6) { i -> Dice(_dicesImg[i]) }
        diceDrawables = listOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)
        scoreList = ArrayList(12)
    }

    // Rolls dices for new game (everything is cleared)
    fun rollNewGame() {

        overallScore = 0
        updateOverallScore(0)
        rollScore = Pair("None", 0)
        scoreList.clear()
        rollNewHand()
    }

    // Rolls dices for new hand (updates overall score with best roll score)
    fun rollNewHand() {
        if(rollScore.first != "None") {
            scoreList.add(rollScore.first)
            updateOverallScore(rollScore.second)
        }

        for(dice in dices) {
            dice.resetDice()
        }
        rollDice()
    }

    // Rolls dices every 10 times in 1 second (changes dice drawable every 100ms)
    fun rollDice() {
        if(scoreList.count() >= 12) {
            maxHands?.invoke()
            return
        }

        val rollAnimMaxDuration = 10
        var rollAnimDuration = 0
        val rollAnimHandler = Handler(Looper.getMainLooper())

        rollAnimHandler.post(object : Runnable {
            override fun run() {
                rollAnimDuration += 1
                updateDiceBitmap()

                if(rollAnimDuration >= rollAnimMaxDuration) {
                    val rollScore = calculateCurrentScore()
                    updateRollScore(rollScore)
                    diceRolled?.invoke(rollScore)
                    rollAnimHandler.removeCallbacksAndMessages(null)
                } else {
                    rollAnimHandler.postDelayed(this, 100)
                }
            }
        })
    }

    // Updates bitmap in every dice imageview shown in the activity (only if player is not holding that dice!)
    fun updateDiceBitmap() {
        for (dice in dices) {
            if(!dice.isHolding) {
                val value = dice.rollDice()
                dice.setImageView(diceDrawables[value - 1])
            }
        }
    }

    // Updates current best score in the roll
    fun updateRollScore(score: Pair<String, Int>) {
        rollScore = score
    }

    // Updates overall score with a roll score
    private fun updateOverallScore(score: Int) {
        overallScore += score
        scoreUpdated?.invoke(overallScore)
    }

    // Calculates best roll score that player didn't already took (if you take chance in first hand, in the second hand you can't take again chance)
    private fun calculateCurrentScore(): Pair<String, Int> {
        val scores = ArrayList<Pair<String, Int>>(12)

        scores.add(findOnes())
        scores.add(findTwos())
        scores.add(findThrees())
        scores.add(findFours())
        scores.add(findFives())
        scores.add(findSixes())
        scores.add(findThreeOfKind())
        scores.add(findFourOfKind())
        scores.add(findFullHouse())
        scores.add(findStraight())
        scores.add(findChance())
        scores.add(findYahtzee())

        val sortedScore: List<Pair<String, Int>> = scores.sortedWith(compareBy { it.second })
        val uniqueScoreList = sortedScore.filter { !scoreList.contains(it.first) }
        val uniqueScore = uniqueScoreList[uniqueScoreList.count() - 1]
        return if(uniqueScore.second == 0) Pair("None", 0) else uniqueScore
    }

    // Find same values
    private fun findOnes(): Pair<String, Int> {
        return Pair("Ones", dices.count { it.value == 1 } * 1)
    }

    private fun findTwos(): Pair<String, Int> {
        return Pair("Twos", dices.count { it.value == 2 } * 2)
    }

    private fun findThrees(): Pair<String, Int> {
        return Pair("Threes", dices.count { it.value == 3 } * 3)
    }

    private fun findFours(): Pair<String, Int> {
        return Pair("Fours", dices.count { it.value == 4 } * 4)
    }

    private fun findFives(): Pair<String, Int> {
        return Pair("Fives", dices.count { it.value == 5 } * 5)
    }

    private fun findSixes(): Pair<String, Int> {
        return Pair("Sixes", dices.count { it.value == 6 } * 6)
    }

    // Find two or more same cards
    private fun findThreeOfKind(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.value }.eachCount().filter { it.value >= 3 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            Pair("Three Of Kind", key * value)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Three Of Kind", 0)
        }
    }

    private fun findFourOfKind(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.value }.eachCount().filter { it.value >= 4 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            Pair("Four of Kind", key * value)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Four of Kind", 0)
        }
    }

    private fun findFullHouse(): Pair<String, Int> {
        var dicesThree = dices.groupingBy { it.value }.eachCount().filter { it.value >= 3 }.toList()
        var dicesTwo = dices.groupingBy { it.value }.eachCount().filter { it.value >= 2 }.toList()

        dicesThree = dicesThree.map { it }
        dicesTwo = dicesTwo.filter { it !in dicesThree }

        return try {
            val (keyThree, valueThree) = dicesThree[0]
            val (keyTwo, valueTwo) = dicesTwo[0]
            Pair("Full House", keyThree * valueThree + keyTwo * valueTwo)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Full House", 0)
        }
    }

    private fun findStraight(): Pair<String, Int> {

        val sorted = dices.sortedBy { it.value }
        var sum = 0

        for(i in 1 until sorted.count()) {

            if(sorted[i].value != (sorted[i - 1].value + 1)) {
                break
            }
            sum += sorted[i].value
        }
        return Pair("Straight", sum)
    }

    private fun findChance(): Pair<String, Int> {
        return Pair("Chance", dices.map { it.value }.sum())
    }

    private fun findYahtzee(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.value }.eachCount().filter { it.value >= dices.count() }.toList()
        return Pair("Yahtzee", if(dicesFreq.count() > 0)  50 else 0)
    }
}