package battleship

case class GameState(players: (Player, Player))

class Game(rounds : Int = 1, mode: Int = 0, level: Int = -1) {
  val shipSizes = List(2, 3, 3, 4, 5)
  val renderer = new Renderer // TODO should use companion object

  println(Console.BLUE + "Player 1 place fleet" + Console.WHITE)
  val p1 : Player = mode match {
    case 0 | 1 => Human(number = 1).placeFleet(shipSizes)
    case 2 => Ai(number = 1, level = level - 1).placeFleet(shipSizes)
  }

  println(Console.BLUE + "Player 2 place fleet" + Console.WHITE)
  val p2 : Player = mode match {
    case 0 => Human(number = 2).placeFleet(shipSizes)
    case 1 | 2 => Ai(number = 2, level = level).placeFleet(shipSizes)
  }

  renderer.clear()

  //@tailrec //TODO solve tailrec
  def gameLoop(gameState: GameState): Unit = {
    renderer.render(gameState)
    val pos = gameState.players._1.askForTarget()
    val newPlayers = gameState.players._1.shoot(gameState.players._2, pos)

    if (!newPlayers._2.lost()) { // TODO fix : not working vs AI (game just continues)
      val newGameState = GameState((newPlayers._2, newPlayers._1))
      gameLoop(newGameState)
    }
    else {
      println(Console.GREEN + "Hurray ! Player " + gameState.players._1.number + " won !")
    }
  }

  gameLoop(GameState((p1, p2)))
}
