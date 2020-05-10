/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 * Last modified 4/20/20 9:24 AM
 */

package com.matejarlovic.yahtzee

import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class Yahtzee() {

    var diceRolled: ((Pair<String, Int>) -> Unit)? = null
    var scoreUpdated: ((Int) -> Unit)? = null
    var maxHands: (() -> Unit)? = null

    private var overallScore: Int = 0
    private var dices: List<Dice>
    private var scoreList: ArrayList<String>
    private lateinit var rollScore: Pair<String, Int>

    init {
        diceRolled = null
        scoreUpdated = null

        overallScore = 0
        dices = List(6) { i -> Dice() }
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

        if(scoreList.count() >= 12) {
            maxHands?.invoke()
            return
        }

        for(dice in dices) {
            dice.resetDice()
        }
    }

    // Returns dices list
    fun getDices(): List<Dice> {
        return dices
    }

    // Returns overall score in the game
    fun getOverallScore(): Int {
        return overallScore
    }

    // Sets hold state for the cube
    fun hold(index: Int) {
        this.dices[index].hold()
    }

    // Updates current best score in the roll
    fun updateRollScore(score: Pair<String, Int>) {
        rollScore = score
    }

    // Updates overall score with a roll score
    fun updateOverallScore(score: Int) {
        overallScore += score
    }

    // Calculates best roll score that player didn't already took (if you take chance in first hand, in the second hand you can't take again chance)
    fun calculateCurrentScore(): Pair<String, Int> {
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
        return Pair("Ones", dices.count { it.getValue() == 1 } * 1)
    }

    private fun findTwos(): Pair<String, Int> {
        return Pair("Twos", dices.count { it.getValue() == 2 } * 2)
    }

    private fun findThrees(): Pair<String, Int> {
        return Pair("Threes", dices.count { it.getValue() == 3 } * 3)
    }

    private fun findFours(): Pair<String, Int> {
        return Pair("Fours", dices.count { it.getValue() == 4 } * 4)
    }

    private fun findFives(): Pair<String, Int> {
        return Pair("Fives", dices.count { it.getValue() == 5 } * 5)
    }

    private fun findSixes(): Pair<String, Int> {
        return Pair("Sixes", dices.count { it.getValue() == 6 } * 6)
    }

    // Find two or more same cards
    private fun findThreeOfKind(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.getValue() }.eachCount().filter { it.value >= 3 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            Pair("Three Of Kind", (key * value) + 20)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Three Of Kind", 0)
        }
    }

    private fun findFourOfKind(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.getValue() }.eachCount().filter { it.value >= 4 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            Pair("Four of Kind", (key * value) + 40)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Four of Kind", 0)
        }
    }

    private fun findFullHouse(): Pair<String, Int> {
        var dicesThree = dices.groupingBy { it.getValue() }.eachCount().filter { it.value >= 3 }.toList()
        var dicesTwo = dices.groupingBy { it.getValue() }.eachCount().filter { it.value >= 2 }.toList()

        dicesThree = dicesThree.map { it }
        dicesTwo = dicesTwo.filter { it !in dicesThree }

        return try {
            val (keyThree, valueThree) = dicesThree[0]
            val (keyTwo, valueTwo) = dicesTwo[0]
            Pair("Full House", (keyThree * valueThree + keyTwo * valueTwo) + 30)
        } catch (e: IndexOutOfBoundsException) {
            Pair("Full House", 0)
        }
    }

    private fun findStraight(): Pair<String, Int> {

        val sorted = dices.sortedBy { it.getValue() }
        var sum = 0

        for(i in 1 until sorted.count()) {

            if(sorted[i].getValue() != (sorted[i - 1].getValue() + 1)) {
                break
            }
            sum += sorted[i].getValue()
        }
        return Pair("Straight", sum + 10)
    }

    private fun findChance(): Pair<String, Int> {
        return Pair("Chance", dices.map { it.getValue() }.sum())
    }

    private fun findYahtzee(): Pair<String, Int> {
        val dicesFreq = dices.groupingBy { it.getValue() }.eachCount().filter { it.value >= dices.count() }.toList()
        return Pair("Yahtzee", if(dicesFreq.count() > 0)  dices.map { it.getValue() }.sum() + 50 else 0)
    }
}