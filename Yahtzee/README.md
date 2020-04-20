# Yahtzee Game

This is a simple representation of a Yahtzee game with 6 dices. This Kotlin Android application was written to solve an assignment from a college course at FERIT Osijek.

Features
----
- Animated changing of 6 Yahtzee cubes
- The ability to hold the dice
- Calculating the best score in the current roll
- Calculating all results in Yahtzee with 6 dice (Yahtzee, Full House, Straight, etc.)
- Possibility of throwing a new hand after 3 rolls
- Ability to start a new game

Scoring Rules
----
### Chance: 
The player scores the sum of all dice, no matter what they read.
Example:
-   1,1,3,3,6,3 chance score will be 17 (1+1+3+3+6+3)
-   4,5,5,6,1,2 chance score will be 23 (4+5+5+6+1+2)

### Ones, Twos, Threes, Fours, Fives, Sixes: 
The player scores the sum of the dice that reads one, two, three, four, five or six, respectively. 
Example:
-   1,1,3,2,4,4 fours score will be 8 (4+4)
-   2,3,1,2,5,1 twos score will be 4 (2+2)
-   3,3,3,2,4,5 ones score will be 0

### Straight: 
If the dice values are in incremental order.
Example:
-    1,2,3,4,5,6 straight score will be sum of all dices + 10 bonus points

### Three of a kind: 
If there are three dice with the same number. 
Example:
-    3,3,3,2,4,5 three of kind score will be 9 (3+3+3) + 20 bonus points
-    3,1,3,4,5,6 three of kind score will be 0
-    3,6,3,3,3,1 three of kind score will be 9 (3+3+3) + 20 bonus points


### Full House:
If the dice are two of a kind and three of a kind. 
For example, when placed on "full house":
   
-    1,1,2,2,2,6 full house score will be 8 (1+1+2+2+2) + 30 bonus points
-    2,2,5,3,3,4 full house score will be 0
-    4,4,4,4,4,4 full house score will be 0

### Four of a kind: 
If there are four dice with the same number, the player scores the sum of these dice. 
Example:
-    2,2,1,2,2,5 four of kind score will be 8 (2+2+2+2) + 40 bonus points
-    2,5,2,2,5,5 four of kind score will be 0
-    2,2,1,2,2,2 four of kind score will be 8 (2+2+2+2) + 40 bonus points


### Yahtzee: 
If all dice have the same number. 
Example:
-   1,1,1,1,1,1 yahtzee score will be 6 (1+1+1+1+1+1) + 50 bonus points
-   1,1,1,2,1,1 yahtzee score will be 0


Game rules reference: http://grail.sourceforge.net/demo/yahtzee/rules.html
Game bonus points reference: https://geek.hr/e-kako/zabava/kako-se-igra-jamb/

Installation
----
Clone Git repository:
```
git clone https://github.com/B-Matt/rma-lv1.git
```
And open the Yahtzee project in the Android Studio.

License
----
MIT