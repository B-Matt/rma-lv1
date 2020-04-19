package com.matejarlovic.yahtzee

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.lang.IndexOutOfBoundsException

class Yahtzee(_dicesImg: List<ImageView>) {

    private var dices: List<Dice> = List(6) { i -> Dice(i, _dicesImg[i]) }
    private var diceSprites = listOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)

    fun rollDice() {
        val rollAnimMaxDuration = 10
        var rollAnimDuration = 0
        val rollAnimHandler = Handler(Looper.getMainLooper())

        rollAnimHandler.post(object : Runnable {
            override fun run() {
                rollAnimDuration += 1
                updateDiceBitmap()

                if(rollAnimDuration >= rollAnimMaxDuration) {
                    rollAnimHandler.removeCallbacksAndMessages(null)
                } else {
                    rollAnimHandler.postDelayed(this, 100)
                }
            }
        })
    }

    fun updateDiceBitmap() {
        for (dice in dices) {
            if(!dice.isHolding) {
                val value = dice.rollDice()
                dice.setImageView(diceSprites[value - 1])
            }
        }
    }

    // Find same values
    fun findSameValue(refValue: Int): Int {
        var sameDicesCounter = 0
        for (dice in dices) {
            if(dice.currentValue == refValue) {
                sameDicesCounter += 1
            }
        }
        return sameDicesCounter
    }

    fun findOnes(): Int {
        return findSameValue(1) * 1
    }

    fun findTwos(): Int {
        return findSameValue(2) * 2
    }

    fun findThrees(): Int {
        return findSameValue(3) * 3
    }

    fun findFours(): Int {
        return findSameValue(4) * 4
    }

    fun findFives(): Int {
        return findSameValue(5) * 5
    }

    fun findSixes(): Int {
        return findSameValue(6) * 6
    }

    // Find two or more same cards
    fun findThreeOfKind(): Int {

        val dicesFreq = dices.groupingBy { it.currentValue }.eachCount().filter { it.value >= 3 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            key * value
        } catch (e: IndexOutOfBoundsException) {
            0
        }
    }

    fun findFourOfKind(): Int {
        val dicesFreq = dices.groupingBy { it.currentValue }.eachCount().filter { it.value >= 4 }.toList()
        return try {
            val (key, value) = dicesFreq[0]
            key * value
        } catch (e: IndexOutOfBoundsException) {
            0
        }
    }

    fun findFullHouse(): Int {
        var dicesThree = dices.groupingBy { it.currentValue }.eachCount().filter { it.value >= 3 }.toList()
        var dicesTwo = dices.groupingBy { it.currentValue }.eachCount().filter { it.value >= 2 }.toList()

        dicesThree = dicesThree.map { it }
        dicesTwo = dicesTwo.filter { it !in dicesThree }

        return try {
            val (keyThree, valueThree) = dicesThree[0]
            val (keyTwo, valueTwo) = dicesTwo[0]
            keyThree * valueThree + keyTwo * valueTwo
        } catch (e: IndexOutOfBoundsException) {
            0
        }
    }

    fun findStraight(): Int {

        val sorted = dices.sortedBy { it.currentValue }
        var sum = 0

        for(i in 1 until sorted.count()) {

            if(sorted[i].currentValue != (sorted[i - 1].currentValue + 1)) {
                break
            }
            sum += sorted[i].currentValue
        }
        return sum
    }

    fun findChance(): Int {
        return dices.map { it.currentValue }.sum()
    }

    fun findYahtzee(): Int {

        val dicesFreq = dices.groupingBy { it.currentValue }.eachCount().filter { it.value >= dices.count() }.toList()
        return try {
            50
        } catch (e: IndexOutOfBoundsException) {
            0
        }
    }
}