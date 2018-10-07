# battleship  [![Build Status](https://travis-ci.com/medihebfaiza/battleship.svg?token=wq2jy2ZMbiE2BrbXrnz3&branch=master)](https://travis-ci.com/medihebfaiza/battleship)
A Battleship game in Scala

![alt text](https://imgur.com/yP2GHgy.png "Title Image")
# Description 
This is a very simple and minimalistic classic Battleship game written in scala. It can be played between two players or against AI with three different levels.

The rules of the game are the same as the classic one with the possibilty to play multiple rounds and therefore every player has a score.

To proove that every AI is superior in intelligence to the one before it, the game has a proof mode that runs three different matches with 100 rounds each between the three levels of AI and records the results on a csv file.  
# Installation and Launching
## Dependencies
* scala version 2.12.6
* sbt version 1.2.3
## Instructions
1. Open a Terminal
2. Go to the Game root folder using `cd` command
3. Run with `sbt run` command
## Jar

# How to Play
## Human VS Human
1. Choose mode 0 from the game menu
2. Enter the number of game rounds
3. Each Player must place 5 ships with different sizes on his primary grid and therefore must enter an initial position (ex : 0A) and a direction (horizontal or vertical) for each ship.
4. Each turn a Player must enter a target coordinates meaning a row number and a column letter
5. Each round the grids are reset and each Player must re-place his fleet.  

## Human VS AI
1. Choose mode 1 from the game menu
2. Enter a difficulty level from 0 to 2 (0 being Beginner level and 2 being Hard level)
3. Place your fleet
4. Enter the target cell coordinates each turn

## AI VS AI (Proof)
1. Choose mode 2 from the game menu
2. Let the game run until it stops
3. Check the proof results in the "ai_proof.csv" file on the root folder 

|AI Name     | score | AI Name2  | score2 |
------------ | ------| --------- |  -----   
|AI Beginner | 47    | AI Medium | 53     |
|AI Medium   | 3     | AI Hard   | 97     |
|AI Beginner | 1     | AI Hard   | 99     |

# Architecture
## Pros
## Cons

# AI
## AI0 : The Random One
The first AI uses  random play. It shoots at positions that are randomly generated and it doesn’t keep track of the shot cells so it can shoot a cell multiple times.
This AI makes poor effort and takes too much time to finish a round as the majority of cells should be shot in order to insure that all the ships are sunk.

Speaking of randomness, here’s a random joke :

![alt text](https://i.imgur.com/lJ7LuSg.gif "Title Image")
Credits : [Scott Adams](http://dilbert.com/)
## AI1 : The Dummy with a Memory
The second AI uses a similar strategy to the first one. It’s targets are generated randomly but it avoids shooting at the same cell more than once by using a tracking grid. Every time it shoots a cell, it marks it as hit or missed on its tracking grid. Then, every time it is asked for a target it generates a random one while making sure it was not shot before.

## AI2 : The Hunter
Like the second AI, the third AI keeps track of the shot cells. In addition, this AI “hunts” for cells by stacking the potential targets surrounding a hit cell.
Each time a cell is hit, it adds the surrounding “non shot yet” cells to a set then it starts hunting. The next turn, when it’s asked for a target it gives a target that was popped out from the set.
If the set of potential targets is empty, it goes back to search mode and generates a random target each time until it hits a new cell and so on.

# Post-Mortem

# Author
@medihebfaiza
