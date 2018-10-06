package battleship

import scala.annotation.tailrec

/** GameState is an immutable data structure that stores the state of two Players
  *
  * @param players a Tuple containing two players
  */
case class GameState(players: (Player, Player))

/** Game played between two players in one or multiple rounds
  *
  * @param rounds Number of rounds
  * @param mode   Game mode chosen from the game menu
  * @param level  AI Level (equals to -1 in Human vs Human mode)
  */
class Game(rounds: Int = 1, mode: Int = 0, level: Int = -1) {
  val shipSizes = List(2, 3, 3, 4, 5)

  val p1: Player = mode match {
    case 0 | 1 => Human(number = 1)
    case 2 => Ai(number = 1, level = level)
  }


  val p2: Player = mode match {
    case 0 => Human(number = 2)
    case 2 if level == 2 => Ai(number = 2, level = 0)
    case 2 => Ai(number = 2, level = level + 1)
    case 1 => Ai(number = 2, level = level)
  }

  Renderer.clear()

  /** The battle loop
    * Each loop is a turn, and in each turn a player is asked for a cell coordinates to shoot at his opponent
    * The players are switched in the game state after each loop
    *
    * @param gameState has the data about the two players
    * @return a new GameState
    */
  @tailrec //TODO solve tailrec
  final def battleLoop(gameState: GameState): GameState = {
    //Renderer.render(gameState)
    val pos = gameState.players._1.askForTarget()
    val newPlayers = gameState.players._1.shoot(gameState.players._2, pos)

    if (!newPlayers._2.lost()) {
      val newGameState = GameState((newPlayers._2, newPlayers._1))
      battleLoop(newGameState)
    }
    else {
      println(Console.GREEN + "Hurray ! Player " + gameState.players._1.number + " won !")
      GameState((gameState.players._2, gameState.players._1.incrementScore))
    }
  }

  // TODO Problem when placing fleet in the second loop
  /** The main game loop
    * Each loop is a round, and in each round the players grids and fleets are reset
    * Then players are asked to replace their fleet again
    *
    * @param round     number of rounds
    * @param gameState the game state
    * @return the last game state which contains the last score between the two players
    */
  @tailrec
  final def gameLoop(round: Int = 0, gameState: GameState): GameState = {
    if (round < rounds) {
      // TODO should check who started first the round before and make him start second
      println(Console.BLUE + "Player " + gameState.players._1.number + " place fleet" + Console.WHITE)
      val newP1 = gameState.players._1.reset().placeFleet(shipSizes)
      println()
      println(Console.BLUE + "Player " + gameState.players._2.number + " place fleet" + Console.WHITE)
      val newP2 = gameState.players._2.reset().placeFleet(shipSizes)
      val newGameState = GameState((newP1, newP2))
      gameLoop(round + 1, battleLoop(newGameState))
    }
    else {
      println("Player " + gameState.players._1.number + " score :")
      println(gameState.players._1.score)
      println("Player " + gameState.players._2.number + " score :")
      println(gameState.players._2.score)
      gameState
    }
  }

  /** Start a game loop and return the last game state
    *
    * @return last game state
    */
  def start(): GameState = {
    gameLoop(gameState = GameState((p1, p2)))
  }

}

/** Factory for [[battleship.Game]] instances. */
object Game {
  def apply(rounds: Int = 1, mode: Int = 0, level: Int = -1): Game = new Game(rounds, mode, level)
}