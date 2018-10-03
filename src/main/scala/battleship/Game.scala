package battleship

case class GameState(players: (Player, Player))

class Game(ai: Boolean = false) {
  val shipSizes = List(2, 3, 3, 4, 5)
  val renderer = new Renderer // TODO should use companion object

  println(Console.BLUE + "Player 1 place fleet" + Console.WHITE)
  //var p1 = Human().placeFleet(shipSizes)
  var p1 = Ai(number = 1, level = 0).placeFleet(shipSizes)

  renderer.clear()

  println(Console.BLUE + "Player 2 place fleet" + Console.WHITE)
  var p2 = ai match {
    case false => Human(number = 2).placeFleet(shipSizes)
    case true => Ai(number = 2, level = 2).placeFleet(shipSizes)
  }

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
