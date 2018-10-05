package battleship

case class GameState(players: (Player, Player))

class Game(rounds : Int = 1, mode: Int = 0, level: Int = -1) {
  val shipSizes = List(2, 3, 3, 4, 5)

  val p1 : Player = mode match {
    case 0 | 1 => Human(number = 1).placeFleet(shipSizes)
    case 2 => Ai(number = 1, level = level)
  }


  val p2 : Player = mode match {
    case 0 => Human(number = 2).placeFleet(shipSizes)
    case 2 if level == 2 => Ai(number = 2, level = 0)
    case 2 | 1 => Ai(number = 2, level = level + 1)
  }

  Renderer.clear()

  //@tailrec //TODO solve tailrec
  def battleLoop(gameState: GameState): GameState = {
    //Renderer(gameState)
    val pos = gameState.players._1.askForTarget()
    val newPlayers = gameState.players._1.shoot(gameState.players._2, pos)

    if (!newPlayers._2.lost()) { // TODO fix : not working vs AI (game just continues)
      val newGameState = GameState((newPlayers._2, newPlayers._1))
      battleLoop(newGameState)
    }
    else {
      println(Console.GREEN + "Hurray ! Player " + gameState.players._1.number + " won !")
      GameState((gameState.players._2, gameState.players._1.incrementScore))
    }
  }

  // TODO Problem when placing fleet in the second loop
  def gameLoop(round: Int = 0, gameState: GameState): GameState = {
    if (round < rounds) {
      // TODO should check who started first the round before and make him start second
      println(Console.BLUE + "Player " + gameState.players._1.number + " place fleet" + Console.WHITE)
      val newP1 = gameState.players._1.reset().placeFleet(shipSizes)
      println()
      println(Console.BLUE + "Player " + gameState.players._2.number + " place fleet" + Console.WHITE)
      val newP2 = gameState.players._2.reset().placeFleet(shipSizes)
      val newGameState = GameState((newP1, newP2))
      gameLoop(round + 1 , battleLoop(newGameState))
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
  def start() : GameState = {
    gameLoop(gameState = GameState((p1, p2)))
  }

}


object Game {
  def apply(rounds: Int = 1, mode: Int = 0, level: Int = -1): Game = new Game(rounds, mode, level)

}